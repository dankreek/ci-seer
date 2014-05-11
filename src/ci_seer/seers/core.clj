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
;;; CiSeer Protocol

(defprotocol CiSeer
  "Defines operations which can be performed on a CI service."

  (supports? [this ci-system]
    "Does the implementor of this protocol support the given ci-system?")

  (get-jobs-in-folder [this server-context folder]
    "Get a list of all the jobs available on the server in the folder. Each job
    matches the JobStatus schema.")

  (get-job-status [this server-context job-name]
    "Get the current status of the provided job."))
