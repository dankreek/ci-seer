(ns seers.seer-protocol
  (:import (java.net URL))
  (:require [schema.core :as schema]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Schemas

(def JobStatus
  (schema/enum [:running :failing :passing :disabled]))

(def ServerContext
  {:url URL
   :type JobStatus})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; CiSeer Protocol

(defprotocol CiSeer
  "Defines operations which can be performed on a CI service."

  (supports? [this ci-system]
    "Does the implementor of this protocol support the given ci-system?")

  (get-jobs-in-folder [this server-context folder]
    "Get a list of all the jobs available on the server in the folder. Each job
    object contains info on ")

  (get-job-status [this server-context job-name]
    "Get the current status of the provided job."))
