(ns ci-seer.services.pr-seer.core
  (:import (clojure.lang Atom))
  (:require [ci-seer.pr-seers.core :as seers]
            [schema.core :as schema]
            [ci-seer.util :as util]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Defines

(def default-pr-seers
  "The default PR Seers to load if none are specified in the config."
  ["ci-seer.pr-seers.github/pr-seer"])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def PrSeerContext
  "Each Seer"
  {:seer    (schema/protocol seers/PrSeer)
   :context schema/Any})

(def PrServiceContext
  "The PR-Seer service context. A map of keywords which respresent the supported
  PR-seers to their Seer implementation and context."
  {schema/Keyword PrSeerContext})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utils

(schema/defn seer-context :- PrServiceContext
  [config
   seer :- (schema/protocol seers/PrSeer)]
  (let [id (seers/supported-system seer)]
    {id {:seer    seer
         :context (seers/config->context seer (get config id))}}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(schema/defn config->context :- PrServiceContext
  "Create a service context map from the global configuration map."
  [config]
  (let [seers-list (-> (or (:pr-seers config) default-pr-seers)
                       util/resolve-idents)]
    (apply merge (map (partial seer-context config) seers-list))))
