(ns ci-seer.util
  (:import (java.io FileNotFoundException))
  (:require [clojure.string :as string]))

(defn resolve-ident
  "Given a string which represents a fully-qualified identifier in the form of
  `namespace/identifier` find it on the classpath and return it."
  [fq-name]
  (try
    (let [[ns seer-name] (string/split fq-name #"/")
          _              (require [(symbol ns)])
          ident          (ns-resolve (symbol ns) (symbol seer-name))]
      @ident)
    (catch FileNotFoundException _
      ;; TODO: Use slingshot to bubble up a more useful exception
      (throw (IllegalStateException. (str "The identifier '" fq-name "' could "
                                          "not be found."))))))

(defn resolve-idents
  "Given a list of strings which repesent fully-qualified identifiers, in the
  form of `namespace/identifier`, return a list of the resolved identifiers."
  [l]
  (map resolve-ident l))
