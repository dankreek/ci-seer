(ns ci-seer.seers.core
  (:import (java.net URL)
           (org.joda.time DateTime))
  (:require [schema.core :as schema]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def JobStatus
  "An enumeration of the different types of job statuses which will be returned
  by the get-job-status method."
  {;; The printable label of the job
   :label schema/Str
   ;; The unqiue name of the job as it exists on the CI server.
   :name schema/Str
   ;; Is this job in the build queue?
   :in-build-queue schema/Bool
   ;; The result of the last build attempt, nil if no last build exists
   :status (schema/enum :failing
                        :unstable
                        :passing
                        :pending
                        :disabled
                        :aborted
                        :notbuilt)
   ;; If a job is currently running then this map will be set
   :running-job (schema/maybe {;; The date and time the job started
                               :start-time DateTime
                               ;; Amount of time the running job should take (in ms)
                               :estimated-duration (schema/maybe schema/Int)})})

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
  "The functions needed to query the job statuses of a CI system."

  (supported-system [this]
    "A keywords representing the CI systems this Seer supports.")

  (get-jobs-in-folder [this url folder]
    "Get a list of all the jobs available on the server in the folder. Each job
    matches the JobStatus schema.")

  (get-job [this url name]
    "Get a single job from the server. The job matches the JobStatus schema."))
