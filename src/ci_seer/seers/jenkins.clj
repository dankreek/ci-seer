(ns ci-seer.seers.jenkins
  (:require [ci-seer.seers.core :as core]
            [cheshire.core :as cheshire]
            [schema.core :as schema]
            [clj-http.client :as client]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def JenkinsJob
  {:name schema/Str
   :displayName schema/Str
   :color schema/Str
   schema/Keyword schema/Any})

(def JenkinsView
  {:jobs [JenkinsJob]
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
  jenkins-job->job-status :- core/JobStatus
  [job :- JenkinsJob]
  (let [{:keys [name color]} job]
    {:name  name
     :status (case color
               "red" :failing
               "blue" :passing
               "disabled" :disabled
               "aborted" :aborted)}))

(schema/defn ^:always-validate
  jenkins-view->seer-jobs :- [core/JobStatus]
  [parsed-view :- JenkinsView]
  (mapv jenkins-job->job-status (:jobs parsed-view)))

(schema/defn ^:always-validate
  parse-view-payload :- JenkinsView
  "Parse the data returned from fetch-view-payload into
  a data structure."
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
            (parse-view-payload)
            (jenkins-view->seer-jobs))))

    (get-job-status
      [this server-context job]
      {:pre [(schema/check core/ServerConfig server-context)
             (string? job)]})))
