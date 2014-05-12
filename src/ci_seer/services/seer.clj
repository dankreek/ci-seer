(ns ci-seer.services.seer
  (:require [puppetlabs.trapperkeeper.core :as tk]
            [clojure.tools.logging :as log]))

(defprotocol Seer
  "The Trapperkeeper Seer service. Currently this service does not require
  any functions.")

(tk/defservice seer-service
  "Seer service."
  Seer
  [[:ConfigService get-in-config]]
  (init [this context]
        (log/info "Initializing.")
        context)

  (start [this context]
         (log/info "Starting.")
         context)

  (stop [this context]
        (log/info "Stopping.")
        context))
