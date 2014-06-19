(ns ci-seer.services.ci-seer.core
  (:import (java.net URL))
  (:require [ci-seer.seers.core :as seers]
            [clojure.string :as string]
            [schema.core :as schema]
            [clojure.tools.logging :as log]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Constants

(def default-seers
  "If no seers are defined in config, use these by default."
  ["ci-seer.seers.jenkins/seer"])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Schemas

(def CiServerConfig
  "The map that describes each CI server when read from config."
  {;; The type of Seer this server is
   :type schema/Str
   ;; Base URL to the CI system's API
   :url schema/Str
   ;; List of folders (or views on Jenkins) to display jobs from
   (schema/optional-key :folders) [schema/Str]
   ;; List of single jobs to display
   (schema/optional-key :jobs) [schema/Str]})

(def CiServerContext
  "The map that describes each CI server when stored inside the ci-seer
  service's context. This is the same as CiServerConfig, but with strongly
  typed values that have been parsed from their string forms."
  {:type schema/Keyword
   :url URL
   (schema/optional-key :folders) [schema/Str]
   (schema/optional-key :jobs) [schema/Str]})

(def Config
  "Defines the Seer service's configuration. This is a structure that is read
  from the Trapperkeeper config."
  {;; List of fully-qualified Seer objects to load.
   (schema/optional-key :seers) [schema/Str]
   ;; List of CI servers to pull job info from.
   :servers [CiServerConfig]})

(def SeersMap
  "A mapping from the Seer's type to its implementation."
  {schema/Keyword (schema/protocol seers/CiSeer)})

(def ServiceContext
  "The CI-Seer service context."
  {;; The service's configuration.
   :servers [CiServerContext]
   ;; A map of Seer types to CiSeer instances.
   :seers SeersMap})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utilities

(schema/defn ^:always-validate
  resolve-seer :- SeersMap
  "Resolve a Seer by its fully-qualified name."
  [fq-seer-name :- schema/Str]
  {:pre [(string? fq-seer-name)]}
  (try
    (let [[ns seer-name] (string/split fq-seer-name #"/")
          _ (require [(symbol ns)])
          seer (ns-resolve (symbol ns) (symbol seer-name))]
      {(seers/supported-system @seer) @seer})
    (catch FileNotFoundException _
      (throw (IllegalStateException. (str "The seer '" fq-seer-name "' could "
                                          "not be found."))))))

(schema/defn ^:always-validate
  generate-seer-map :- SeersMap
  "Create a map of server types to their corresponding Seer objects by asking
  each Seer what its type is and storing it at that key."
  [seer-names :- [schema/Str]]
  (apply merge (map resolve-seer seer-names)))

(schema/defn ^:always-validate
  collect-seers :- SeersMap
  "Generate a map of configured Seers by either using the list provided by the
  config or a default list of seers if none was provided."
  [config :- Config]
  (let [seers-list (or (:seers config) default-seers)]
    (generate-seer-map seers-list)))

(schema/defn ^:always-validate
  server-type->keyword :- CiServerContext
  "In the config the server type is most likely stated as a string, this should
  be converted to a keyword."
  [server-map :- CiServerConfig]
  (try
    (let [{:keys [type url folders jobs]} server-map]
      {:type    (keyword type)
       :url     (URL. url)
       :folders folders
       :jobs    jobs})
    (catch MalformedURLException e
      (throw (IllegalStateException. (str "Could not parse the URL for the "
                                          "server configured as "
                                          server-map ": " (.getMessage e)))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Core functions

(schema/defn ^:always-validate
  validate-context :- ServiceContext
  "Validate that each server in the config has a Seer. If no Seer type is found
  for a server then an exception is thrown, otherwise the provided context is
  returned."
  [context :- ServiceContext]
  (doseq [server (:servers context)]
    (let [{:keys [url type folders jobs]} server
          seers (:seers context)]
      (when-not (contains? seers type)
        (throw (IllegalStateException. (str "There is no Seer registered with "
                                            "the type " type " for the server "
                                            "configured as " server))))
      (when-not (or folders jobs)
        (throw (IllegalStateException. (str "A list of jobs or folders must be "
                                            "provided in the server configured "
                                            "as " server))))

      (let [protocol (.getProtocol url)]
        (when-not (or (= "http" protocol) (= "https" protocol))
          (throw (IllegalStateException. (str "The " protocol " protocol is "
                                              "not supported for the ci server's "
                                              "url. Only http and https "
                                              "are supported.")))))))
  context)

(schema/defn ^:always-validate
  config->context :- ServiceContext
  "Convert the input config provided by Trapperkeepr into a service context map."
  [config :- Config]
  (let [config-servers (:servers config)]
    {:seers   (collect-seers config)
     :servers (map server-type->keyword config-servers)}))
