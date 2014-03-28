(def tk-version "0.3.4")

(defproject dankreek/ci-seer "0.1.0-SNAPSHOT"
  :description "A Clojure-based CI radiator"

  :url "https://github.com/dankreek/ci-seer"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [puppetlabs/trapperkeeper ~tk-version]]

  :main ^:skip-aot dankreek.ci-seer

  :target-path "target/%s"

  :profiles  {:uberjar {:aot :all}

              :dev {:test-paths    ["test-resources"]
                    :dependencies  [[org.clojure/tools.namespace "0.2.4"]
                                    [puppetlabs/trapperkeeper ~tk-version :classifier "test" :scope "test"]
                                    [spyscope "0.1.4"]]
                    :injections    [(require 'spyscope.core)]}})
