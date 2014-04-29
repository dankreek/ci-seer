(ns seers.jenkins
  (:require [seers.seer-protocol :as protocol]
            [cheshire.core :as cheshire]
            [schema.core :as schema]
            [clj-http.client :as client]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;o;;;;;;;;;;;;;;;;;;
;;; Schemas

(def JenkinsJob
  {:name schema/Str
   :displayName schema/Str
   :color schema/Str
   ;; Don't care about the rest of the keys
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

(schema/defn parse-view-payload :- JenkinsView
  "Parse the data returned from fetch-view-payload into
  a data structure."
  [json-string :- schema/Str]
  (cheshire/parse-string json-string true))

(defn job-status
  [view-data]
  {:post [(protocol/status? %)]}
  :passing)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(def seer
  (reify protocol/CiSeer
    (supports? [this ci-system]
      (= ci-system :jenkins))

    (get-jobs-in-folder [this server folder]
      {:pre [(protocol/server? server)
             (string? folder)]}
      [])

    (get-job-status [this server job]
      {:pre [(protocol/server? server)
             (string? job)]})))
