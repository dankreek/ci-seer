(ns ci-seer.services.ci-seer.core
  (:require [ci-seer.seers.jenkins :as jenkins]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Defines

(def seers
  "A list of all seers available to the ci-seer service."
  [ jenkins/seer ])

