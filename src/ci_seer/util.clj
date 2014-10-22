(ns ci-seer.util
  (:import (java.io FileNotFoundException))
  (:require [clojure.string :as string]))

(defn resolve-ident
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
