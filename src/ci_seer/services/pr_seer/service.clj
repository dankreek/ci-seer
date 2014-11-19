(ns ci-seer.services.pr-seer.service
  (:require [puppetlabs.trapperkeeper.core :as tk]
            [clojure.tools.logging :as log]
            [ci-seer.services.pr-seer.core :as core]))


(tk/defservice pr-seer-service
  "Service for querying all the configured PR seers."
  [[:ConfigService get-config]]
  (init
    [this context]
    (log/info "Initializing PR-seer service.")
    (merge context (core/config->context get-config)))

  (start
    [this context]
    (log/info "Starting PR-seer service.")
    context)

  (stop
    [this context]
    (log/info "Stopping PR-seer service.")
    context)

  (total-open-prs
    "Returns the total number of open PRs for every configured server and repo."
    [this]
    0)

  (open-prs
    "Returns a list of maps describing each configured server and the number of
    outstanding PRs."
    [this]
    0))
