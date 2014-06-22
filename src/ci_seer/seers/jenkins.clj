(ns ci-seer.seers.jenkins
  (:require [ci-seer.seers.core :as core]
            [cheshire.core :as cheshire]
            [schema.core :as schema]
            [clj-http.client :as client]
            [clojure.string :as string]
            [clj-time.coerce :as ctime]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def JenkinsJob
  "How Jenkins describes a job with JSON."
  {:displayName schema/Str
   :name schema/Str
   :color schema/Str
   :inQueue schema/Bool
   :lastBuild (schema/maybe {:building          schema/Bool
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

(def job-tree-fields
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
  (let [path (str "/view/" view "/api/json?tree=name,jobs["
                  job-tree-fields "]")]
    (fetch-json base-url path)))

(defn fetch-job-payload
  "Fetch the JSON payload representing a single job on a jenkins server"
  [base-url job]
  {:post [(string? %)]}
  (let [path (str "/job/" job "/api/json?tree=" job-tree-fields)]
    (fetch-json base-url path)))

(schema/defn ^:always-validate
  parsed-job->job-status :- core/JobStatus
  [job :- JenkinsJob]
  (let [in-build-queue (:inQueue job)
        {raw-color :color label :displayName name :name} job
        ;; If the job is building, the color ends with "_anime" *shrug*
        [color _] (string/split raw-color #"_")
        last-build (:lastBuild job)
        estimated-duration (:estimatedDuration last-build)]
    {:name    name
     :label   label
     :in-build-queue in-build-queue
     :status  (case color
                "red"       :failing
                "yellow"    :unstable
                "blue"      :passing
                "grey"      :pending
                "disabled"  :disabled
                "aborted"   :aborted
                "notbuilt"  :notbuilt)
     :running-job (when (:building last-build)
                    {:start-time (ctime/from-long (:timestamp last-build))
                     :estimated-duration (when (>= estimated-duration 0)
                                           estimated-duration)})}))

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
    (supported-system [_] :jenkins)

    (get-jobs-in-folder
      [_ server-context folder]
      {:pre [(schema/check core/ServerConfig server-context)
             (string? folder)
             (= :jenkins (:type server-context))]}
      (let [{url :url} server-context]
        (-> (fetch-view-payload url folder)
            parse-view-payload
            parsed-view->jobs-list)))

    (get-job
      [_ server-context job]
      {:pre [(schema/check core/ServerConfig server-context)
             (string? job)
             (= :jenkins (:type server-context))]}
      (let [{url :url} server-context]
        (-> (fetch-job-payload url job)
            parse-job-payload
            parsed-job->job-status)))))
