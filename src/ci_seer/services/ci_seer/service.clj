(ns ci-seer.services.ci-seer.service
  (:require [ci-seer.services.ci-seer.core :as core]
            [puppetlabs.trapperkeeper.core :as tk]
            [clojure.tools.logging :as log]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Service definition

(tk/defservice seer-service
  "Seer service."
  [[:ConfigService get-in-config]]
  (init [_ context]
        (log/info "Initializing.")
        (merge context
               (-> (get-in-config [:seer])
                   core/config->context
                   core/validate-context)))

  (start [_ context]
         (log/info "Starting.")
         context)

  (stop [_ context]
        (log/info "Stopping.")
        context))
