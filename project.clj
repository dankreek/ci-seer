(def tk-version "0.5.1")

(defproject dankreek/ci-seer "0.1.0-SNAPSHOT"
  :description "A Clojure-based CI radiator"

  :url "https://github.com/dankreek/ci-seer"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [puppetlabs/trapperkeeper ~tk-version]
                 [cheshire "5.3.1"]
                 [prismatic/schema "0.3.0"]
                 [clj-http "1.0.0"]
                 [clj-time "0.8.0"]
                 [tentacles "0.2.5"]]

  :main puppetlabs.trapperkeeper.main

  :repl-options {:init-ns user}

  :profiles  {:uberjar {:aot :all}
              :dev {:source-paths ["dev"]
                    :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                   [puppetlabs/trapperkeeper ~tk-version
                                     :classifier "test"
                                     :scope "test"]]}})
