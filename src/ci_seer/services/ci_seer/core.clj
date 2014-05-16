(ns ci-seer.services.ci-seer.core
  (:require [ci-seer.seers.core :as seers]
            [clojure.string :as string]))

(defn resolve-seer
  "Resolve a Seer by its fully-qualified name."
  [fq-seer-name]
  {:pre [(string? fq-seer-name)]}
  (let [[ns seer-name] (string/split fq-seer-name #"/")]
    (ns-resolve (symbol ns) (symbol seer-name))))

(defn collect-seers
  "Read all the fully qualified Seer names into a list."
  [seer-name-list]
  (map resolve-seer seer-name-list))

