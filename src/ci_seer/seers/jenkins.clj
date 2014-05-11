(ns ci-seer.seers.jenkins
  (:require [ci-seer.seers.core :as core]
            [cheshire.core :as cheshire]
            [schema.core :as schema]
            [clj-http.client :as client]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def ParsedJob
  {:name schema/Str
   :displayName schema/Str
   :color schema/Str
   schema/Keyword schema/Any})

(def ParsedView
  {:jobs [ParsedJob]
   :name schema/Str
   schema/Keyword schema/Any})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Private

(def jenkins-servers (atom {}))

(defn fetch-view-payload
  "Fetch a JSON representing all the contents of a Jenkins
  view, including job names and statuses."
  [url view]
  {:post [(string? %)]}
  (:body (client/get (str url "/view/" view "/api/json?depth=1"))))

(schema/defn ^:always-validate
  parsed-job->job-status :- core/JobStatus
  [job :- ParsedJob]
  (let [{:keys [name color]} job
        [color anime] (.split color "_")]
    {:name    name
     :running (= anime "anime")
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
  [parsed-view :- ParsedView]
  (mapv parsed-job->job-status (:jobs parsed-view)))

(schema/defn ^:always-validate
  parse-view-payload :- ParsedView
  "Parse the data returned from fetch-view-payload into a data structure."
  [json-string :- schema/Str]
  (cheshire/parse-string json-string true))

(schema/defn ^:always-validate
  job-status :- core/JobStatus
  [view-data :- ParsedView
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
            (parse-view-payload)
            (parsed-view->jobs-list))))

    (get-job-status
      [this server-context job]
      {:pre [(schema/check core/ServerConfig server-context)
             (string? job)]})))
