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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Private

(defn fetch-view-payload
  "Fetch a JSON representing all the contents of a Jenkins
  view, including job names and statuses."
  [url view]
  {:post [(string? %)]}
  (let [full-url (str url "/view/" view "/api/json?tree="
                      (string/join "," view-fields) ","
                      "jobs[" (string/join "," job-fields) ","
                      "lastBuild[" (string/join "," build-fields) ","
                      "culprits[" (string/join "," culprit-fields) "]]]")]
    (:body (client/get full-url))))

(schema/defn ^:always-validate
  parsed-job->job-status :- core/JobStatus
  [job :- JenkinsJob]
  (let [{raw-color :color name :displayName} job
        [color _] (string/split raw-color #"_")
        running (boolean (get-in job [:lastBuild :building]))]
    {:name    name
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
  (mapv parsed-job->job-status (:jobs parsed-view)))

(schema/defn ^:always-validate
  parse-view-payload :- JenkinsView
  "Parse the data returned from fetch-view-payload into a data structure."
  [json-string :- schema/Str]
  (cheshire/parse-string json-string true))

(schema/defn ^:always-validate
  job-status :- core/JobStatus
  [view-data :- JenkinsView
   job :- schema/Str]
  :passing)

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
      [this server-context name]
      nil)))
