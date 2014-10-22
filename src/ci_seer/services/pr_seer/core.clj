(ns ci-seer.services.pr-seer.core
  (:import (clojure.lang Atom))
  (:require [ci-seer.pr-seers.core :as seers]
            [schema.core :as core]
            [schema.core :as schema]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def PrServerContext
  {:type  schema/Keyword
   :repos [{;; The owner of the repoitory
            :user     schema/Str
            ;; The name of the repository
            :repo     schema/Str
            ;; The number of open pull requests on this server
            :open-prs Atom}]})

(def ServiceContext
  "The PR-Seer service context."
  {;; Context map for each configured server
   :servers [PrServerContext]
   ;; A map of PR-seer types to PrSeer implementations
   :seers   {schema/Keyword (schema/protocol seers/PrSeer)}})

(def PrServerConfig
  {:type  schema/Str
   :repos [{:user schema/Str
            :repo schema/Str}]})

(def Config
  "Schema describing the service configuration map."
  {;; List of fully-qualified Seer objects to load. In the form of
   ;; `name.space/seer`
   (schema/optional-key :seers) [schema/Str]
   ;; List of
   :servers [PrServerConfig]})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(schema/defn config->context :- ServiceContext
  "Create a service context map from the service configuration."
  [config :- Config]
  {

    }
  )
