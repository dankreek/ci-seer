(ns ci-seer.services.ci-seer.service
  (:require [ci-seer.services.ci-seer.core :as core]
            [puppetlabs.trapperkeeper.core :as tk]
            [clojure.tools.logging :as log]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Defines

(def default-seers
  "If no seers are defined in config, use these by default."
  ["ci-seer.seers.jenkins/seer"])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Service definition

(tk/defservice seer-service
  "Seer service."
  [[:ConfigService get-in-config]]
  (init [this context]
        (log/info "Initializing.")
        (let [seers-config (get-in-config [:seer :seers])
              seers-list (core/collect-seers (or seers-config
                                                 default-seers))]
          (assoc context :seers seers-list)))

  (start [this context]
         (log/info "Starting.")
         context)

  (stop [this context]
        (log/info "Stopping.")
        context))
