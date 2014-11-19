(ns ci-seer.pr-seers.core
  "Core code for all PR ci-seers."
  (:import  (java.net URL)
            (org.joda.time DateTime))
  (:require [tentacles.pulls :as prs]
            [schema.core :as schema]))

(def PrData
  "Open pull-request data for each repository."
  {:repo      schema/Str
   :repo-url  URL
   :oldest-pr (schema/maybe DateTime)
   :num-prs   schema/Int})

(def PrList [PrData])

(defprotocol PrSeer
  "Methods which acquire info about open pull requests on GitHub."

  (supported-system
    [this]
    "A keyword representing the PR systems this pr-seer supports. This keyword
    is also the key in configuration map which contains this PR-Seer's
    config.")

  (config->context
    [this config]
    "Given a configuration data structure, return a context data structure.")

  (get-pr-data
    [this context]
    "Retrieve a map which describes the open PR state of each repository in the
    context. The return value adheres to the schema defined by `PrList`."))
