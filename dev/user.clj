(ns user
  (:import (java.net URL))
  (:require [clojure.pprint :refer :all]
            [clojure.repl :refer :all]
            [ci-seer.services.ci-seer.service :refer [ci-seer-service]]
            [ci-seer.services.pr-seer.service :refer [pr-seer-service]]
            [clojure.tools.namespace.repl :refer (refresh)]
            [puppetlabs.trapperkeeper.core :as tk]
            [puppetlabs.trapperkeeper.app :as tkapp]))

(def test-config
  "Trapperkeeper config used for CI-Seer development."
  ;; TODO: Move some keys around to put the optional seers configs in other blocks
  {:ci-seers {:seers   ["ci-seer.ci-seers.jenkins/seer"]
              :servers [{:type    "jenkins"
                         :url     "https://jenkins.puppetlabs.com"
                         :folders ["Puppet Server"]
                         :jobs    ["mq"]}]}

   :pr-seers ["ci-seer.pr-seers.github/seer"]

   :github   {:repos ["dankreek/ci-seer"
                      "puppetlabs/puppet-server"]

              :oath-key "XXX"}})

(def system nil)

(defn init []
  (alter-var-root #'system
    (fn [_] (tk/build-app [ci-seer-service] test-config)))
  (alter-var-root #'system tkapp/init)
  (tkapp/check-for-errors! system))

(defn start []
  (alter-var-root #'system
                  (fn [s] (if s (tkapp/start s))))
  (tkapp/check-for-errors! system))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (tkapp/stop s)))))

(defn go []
  (init)
  (start))

(defn context []
  @(tkapp/app-context system))

(defn print-context []
  (pprint (context)))

(defn reset []
  (stop)
  (refresh :after 'user/go))



