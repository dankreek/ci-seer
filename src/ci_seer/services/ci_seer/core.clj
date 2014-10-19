(ns ci-seer.services.ci-seer.core
  (:import (java.net URL MalformedURLException)
           (java.io FileNotFoundException)
           (clojure.lang Atom))
  (:require [ci-seer.ci-seers.core :as seers]
            [clojure.core.async :as async :refer [>! <!]]
            [clojure.string :as string]
            [schema.core :as schema]
            [clojure.tools.logging :as log]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Constants

(def default-seers
  "If no ci-seers are defined in config, use these by default."
  ["ci-seer.ci-seers.jenkins/seer"])

(def refresh-time
  "Amount of time, in milliseconds, to wait between retrieving updates from the
  CI server."
  ;; 1 minute
  ;; TODO: use clj-time to measure this
  (* 1000 60))

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
  {:type                          schema/Keyword
   :url                           URL
   (schema/optional-key :folders) [schema/Str]
   (schema/optional-key :jobs)    [schema/Str]
   ;; A map of job names to current status.
   ;; TODO: See if one can define the schema of what's contained in the atom
   :jobs-status                   Atom})

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

(schema/defn resolve-seer :- SeersMap
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

(schema/defn generate-seer-map :- SeersMap
  "Create a map of server types to their corresponding Seer objects by asking
  each Seer what its type is and storing it at that key."
  [seer-names :- [schema/Str]]
  (apply merge (map resolve-seer seer-names)))

(schema/defn collect-seers :- SeersMap
  "Generate a map of configured Seers by either using the list provided by the
  config or a default list of ci-seers if none was provided."
  [config :- Config]
  (let [seers-list (or (:seers config) default-seers)]
    (generate-seer-map seers-list)))

(schema/defn server-type->keyword :- CiServerContext
  "In the config the server type is most likely stated as a string, this should
  be converted to a keyword."
  [server-map :- CiServerConfig]
  (try
    (let [{:keys [type url folders jobs]} server-map]
      {:type        (keyword type)
       :url         (URL. url)
       :folders     folders
       :jobs        jobs
       :jobs-status (atom {})})
    (catch MalformedURLException e
      (throw (IllegalStateException. (str "Could not parse the URL for the "
                                          "server configured as "
                                          server-map ": " (.getMessage e)))))))

(defn get-seer-for-server
  "Given a CiSeer service context, find the seer which supports "
  [context server]
  (let [type (:type server)
        seers (:seers context)]
    (get seers type)))

(schema/defn index-jobs-list :- {schema/Keyword seers/JobStatus}
  "Create a map of job statuses which are keyed by the job name, converted from
  a string into a keyword. This is done to allow easy updating the job-status
  atom in the server's context."
  [jobs :- [seers/JobStatus]]
  (reduce
    ;; TODO: Make sure interning is actually necessary here
    (fn [acc job] (conj acc {(keyword (:name job)) job}))
    {} jobs))

(schema/defn launch-job-go-block
  "Open an async channel and creates a go block which retrieves the status of
  provided job name on the CI server and puts it on the channel. The block then
  sleeps for "
  [context :- ServiceContext
   server :- CiServerContext
   job :- schema/Str]
  (let [seer (get-seer-for-server context server)
        url (:url server)
        update-channel (async/chan)
        get-updates #(seers/get-job seer url job)]
    (log/info (str "launching query thread for job: '" job "'"))
    ;; TODO: Use a try catch here to properly close the update-channel if the server is unvailable
    (async/go-loop [job-status (get-updates)]
                   (when (>! update-channel (index-jobs-list [job-status]))
                     (<! (async/timeout refresh-time))
                     (recur (get-updates))))
    update-channel))

(schema/defn launch-folder-go-block
  "Opens a channel and creates a go block which gets the status of every job in
  the provided folder, puts it in the channel, then waits to do it again. The
  new channel is returned. If the channel is closed externally the go loop will
  terminate."
  ;; TODO: Factor out common logic from launch-job-go-block
  [context :- ServiceContext
   server :- CiServerContext
   folder :- schema/Str]
  (let [seer (get-seer-for-server context server)
        url (:url server)
        update-channel (async/chan)
        get-updates #(seers/get-jobs-in-folder seer url folder)]
    (log/info (str "launching query thread for folder: '" folder "'"))
    ;; TODO: Use a try catch here to properly close the update-channel if the server is unvailable
    (async/go-loop [jobs-status (get-updates)]
      (when (>! update-channel (index-jobs-list jobs-status))
        (<! (async/timeout refresh-time))
        (recur (get-updates))))
    update-channel))

(schema/defn launch-server-go-blocks
  "Creates an async channel which is a merge of the individual channels which
  recieve job status updates from the jobs and folders on a CI server.
  A go block is launched which continually recieves these updates and in-turn
  updates the jobs-status atom."
  [context :- ServiceContext
   server  :- CiServerContext]
  (log/info (str "launching query threads for server at " (:url server)))
  (let [folder-go-blocks (map #(launch-folder-go-block context server %)
                              (:folders server))
        job-go-blocks    (map #(launch-job-go-block context server %)
                              (:jobs server))
        jobs-channel     (async/merge (concat folder-go-blocks
                                              job-go-blocks))]
    (async/go-loop
      [new-jobs-status (<! jobs-channel)]
      (if-not new-jobs-status
        (log/info "all channels closed for server " (:url server))
        (do
          (log/debug "got job statuses " new-jobs-status "from server "
                    (:url server))
          (swap! (:jobs-status server) #(merge % new-jobs-status))
          (recur (<! jobs-channel)))))

    jobs-channel))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Core functions

(schema/defn validate-context :- ServiceContext
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
                                              "not supported for the ci "
                                              "server's url. Only http and "
                                              "https are supported.")))))))
  context)

(schema/defn config->context :- ServiceContext
  "Convert the input config provided by Trapperkeepr into a service context map."
  [config :- Config]
  (let [config-servers (:servers config)]
    {:seers   (collect-seers config)
     :servers (map server-type->keyword config-servers)}))

(schema/defn launch-seers!
  "Launches go blocks for every job and folder on every server which continually
  query the CI server for the current status of each job and stores them in
  the service context."
  [context :- ServiceContext]
  (doseq [server (:servers context)]
    ;; TODO: Put the channel into the context so that it can be closed on shutdown
    (launch-server-go-blocks context server))
  nil)

(defn get-jobs-status
  [context]
  (reduce conj (map #(vals @(:jobs-status %))
                    (:servers context))))