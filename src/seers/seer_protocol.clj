(ns seers.seer-protocol
  (:import (java.net URL)))

(defprotocol CiSeer
  "Defines operations which can be performed on a CI service."

  (supports? [this ci-system]
    "Does the implementor of this protocol support the given ci-system?")

  (get-jobs-in-folder [this server folder]
    "Get a list of all the jobs available on the server in the folder. Each job
    object contains info on ")

  (get-job-status [this server job-name]
    "Get the current status of the provided job."))

(defn server? [server-map]
  (and (contains? server-map :url)
       (instance? URL (:url server-map))
       (contains? server-map :type)
       (keyword? (:type server-map))))

(defn status? [status-keyword]
  "Is the status-keyword a valid status keyword?"
  (and (keyword? status-keyword)
       (or (= :running  status-keyword)
           (= :failing  status-keyword)
           (= :passing  status-keyword)
           (= :disabled status-keyword))))
