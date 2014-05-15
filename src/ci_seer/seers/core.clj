(ns ci-seer.seers.core
  (:import (java.net URL))
  (:require [schema.core :as schema]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def JobStatus
  "An enumeration of the different types of job statuses which will be returned
  by the get-job-status method."
  {:name schema/Str
   ;; The result of the last build attempt
   :status (schema/enum :failing
                        :unstable
                        :passing
                        :pending
                        :disabled
                        :aborted
                        :notbuilt)
   ;; Is this job currently running?
   :running schema/Bool})

(def ServerConfig
  "The map describing a CI Server's configuration."
  {:url URL
   :type schema/Keyword})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utility Functions

(schema/defn find-job-by-name :- JobStatus
  [jobs-list :- [JobStatus]
   job-name :- schema/Str]
  (first (filter #(= job-name (:name %))
                 jobs-list)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; CiSeer Protocol

(defprotocol CiSeer
  "The functions needed to query the job statuses of a CI system.."

  (supports? [this ci-system]
    "Does the implementor of this protocol support the given ci-system?")

  (get-jobs-in-folder [this server-context folder]
    "Get a list of all the jobs available on the server in the folder. Each job
    matches the JobStatus schema.")

  (get-job [this server-context name]
    "Get a single job from the server. The job matches the JobStatus schema."))
