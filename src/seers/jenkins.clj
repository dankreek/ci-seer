(ns seers.jenkins
  (:require [seers.core :as seers]
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
  parse-view-payload :- JenkinsView
  "Parse the data returned from fetch-view-payload into
  a data structure."
  [json-string :- schema/Str]
  (cheshire/parse-string json-string true))

(schema/defn ^:always-validate
  job-status :- seers/JobStatus
  [view-data :- JenkinsView
   job :- schema/Str]
  :passing)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(def seer
  (reify seers/CiSeer
    (supports?
      [this ci-system]
      (= ci-system :jenkins))

    (get-jobs-in-folder
      [this server-context folder]
      ;; TODO: Put the schema checks in the core functions instead of here
      {:pre [(schema/check seers/ServerConfig server-context)
             (string? folder)]}
      [])

    (get-job-status
      [this server-context job]
      ;; TODO: Put the schema checks in the core functions instead of here
      {:pre [(schema/check seers/ServerConfig server-context)
             (string? job)]})))
