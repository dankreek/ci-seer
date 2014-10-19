(ns ci-seer.pr-seers.core
  "Core code for all PR ci-seers."
  (:import (org.joda.time DateTime))
  (:require [tentacles.pulls :as prs]
            [schema.core :as schema]))

(def PrData
  "Open pull-request data for each repository."
  {:oldest-pr (schema/maybe DateTime)
   :num-prs   schema/Int})

(defprotocol PrSeer
  "Methods which acquire info about open pull requests on GitHub."

  (supported-system
    [this]
    "A keyword representing the PR systems this pr-seer supports.")

  (get-pr-data
    [this user repository auth-info]
    "Retrieve a map which describes the open PR state of a repository which
    adheres to the schema defined by `PrData`."))
