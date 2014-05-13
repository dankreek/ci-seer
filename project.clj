(def tk-version "0.4.2")

(defproject dankreek/ci-seer "0.1.0-SNAPSHOT"
  :description "A Clojure-based CI radiator"

  :url "https://github.com/dankreek/ci-seer"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [puppetlabs/trapperkeeper ~tk-version]
                 [cheshire "5.3.1"]
                 [prismatic/schema "0.2.1"]
                 [clj-http "0.9.1"]
                 [clj-time "0.7.0"]]

  :main puppetlabs.trapperkeeper.main

  :repl-options {:init-ns user}

  :profiles  {:uberjar {:aot :all}
              :dev {:source-paths ["dev"]
                    :dependencies  [[org.clojure/tools.namespace "0.2.4"]
                                    [puppetlabs/trapperkeeper ~tk-version
                                     :classifier "test"
                                     :scope "test"]]}})
