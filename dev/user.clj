(ns user
  (:import (java.net URL))
  (:require [clojure.pprint :refer (pprint)]
            [ci-seer.services.ci-seer.service :refer [seer-service]]
            [clojure.tools.namespace.repl :refer (refresh)]
            [puppetlabs.trapperkeeper.core :as tk]
            [puppetlabs.trapperkeeper.app :as tkapp]))

(def system nil)

(defn init []
  (alter-var-root
    #'system
    (fn [_]
      (let [app (tk/build-app
                  [seer-service]
                  {:seer {:servers [{:type "jenkins"
                                     :url "https://jenkins.puppetlabs.com"
                                     :folders ["clojure"]}]}})]
        (tkapp/init app))))
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

(defn jenkins-context
  [url]
  {:url (URL. url)
   :type :jenkins})

