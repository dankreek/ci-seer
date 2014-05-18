(ns ci-seer.seers.jenkins
  (:require [ci-seer.seers.core :as core]
            [cheshire.core :as cheshire]
            [schema.core :as schema]
            [clj-http.client :as client]
            [clojure.string :as string]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def JenkinsJob
  "The schema defining how Jenkins describes a job."
  {:displayName schema/Str
   :name schema/Str
   :color schema/Str
   :inQueue schema/Bool
   :lastBuild   (schema/maybe {:building          schema/Bool
                               :duration          schema/Int
                               :estimatedDuration schema/Int
                               :timestamp         schema/Int
                               :culprits          [{:id schema/Str}]})})

(def JenkinsView
  {:jobs [JenkinsJob]
   :name schema/Str})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Defines

(def view-fields
  ["name"])

(def job-fields
  ["name", "displayName", "color", "inQueue"])

(def build-fields
  ["building", "timestamp", "duration", "estimatedDuration"])

(def culprit-fields
  ["id"])

(def job-tree
  (str (string/join "," job-fields) ","
       "lastBuild[" (string/join "," build-fields) ","
       "culprits[" (string/join "," culprit-fields) "]]"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Private

(defn fetch-json
  [base-url path]
  (let [full-url (str base-url path)]
    (:body (client/get full-url))))

(defn fetch-view-payload
  "Fetch the JSON representing all the contents of a Jenkins
  view, including job names and statuses."
  [base-url view]
  {:post [(string? %)]}
  (let [path (str "/view/" view "/api/json?tree=name,jobs[" job-tree "]")]
    (fetch-json base-url path)))

(defn fetch-job-payload
  "Fetch the JSON payload representing a single job on a jenkins server"
  [base-url job]
  {:post [(string? %)]}
  (let [path (str "/job/" job "/api/json?tree=" job-tree)]
    (fetch-json base-url path)))

(schema/defn ^:always-validate
  parsed-job->job-status :- core/JobStatus
  [job :- JenkinsJob]
  (let [{raw-color :color label :displayName name :name} job
        [color _] (string/split raw-color #"_")
        running (boolean (get-in job [:lastBuild :building]))]
    {:name    name
     :label   label
     :running running
     :status  (case color
                "red"      :failing
                "yellow"   :unstable
                "blue"     :passing
                "grey"     :pending
                "disabled" :disabled
                "aborted"  :aborted
                "nobuilt"  :notbuilt)}))

(schema/defn ^:always-validate
  parsed-view->jobs-list :- [core/JobStatus]
  [parsed-view :- JenkinsView]
  (map parsed-job->job-status (:jobs parsed-view)))

(schema/defn ^:always-validate
  parse-view-payload :- JenkinsView
  "Parse the data returned from fetch-view-payload into a data structure."
  [json-string :- schema/Str]
  (cheshire/parse-string json-string true))

(schema/defn ^:always-validate
  parse-job-payload :- JenkinsJob
  "Parse the data returned from fetch-job-payload into a data structure."
  [json-string :- schema/Str]
  (cheshire/parse-string json-string true))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(def seer
  (reify core/CiSeer
    (supports?
      [this ci-system]
      (= ci-system :jenkins))

    (get-jobs-in-folder
      [this server-context folder]
      {:pre [(schema/check core/ServerConfig server-context)
             (string? folder)]}
      (let [{url :url} server-context]
        (-> (fetch-view-payload url folder)
            parse-view-payload
            parsed-view->jobs-list)))

    (get-job
      [this server-context job]
      {:pre [(schema/check core/ServerConfig server-context)
             (string? job)]}
      (let [{url :url} server-context]
        (-> (fetch-job-payload url job)
            parse-job-payload
            parsed-job->job-status)))))
