(ns seers.testicleeze
  (:require [ci-seer.seers.core :as core]
            [clojure.java.io :as io]
            [clojure.string :as str]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Defines

(def words-list-file "dev-resources/tech_words_list")

(def words-list
  "A vector full of tech words and phrases."
  (with-open [rdr (io/reader words-list-file)]
    (mapv #(str/trim %) (line-seq rdr))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utilities

(defn random-job-name
  "Randomly generate a job name out of a list of tech terms, where `n` is the
  number of terms to use to generate the name. If no number is given then a
  random number between 1 and 3 will be used."
  ([] (random-job-name (+ 1 (rand-int 3))))
  ([n]
   {:pre [(> n 0)]
    :post [(string? %)]}
   (str/join " " (repeatedly n #(rand-nth words-list)))))



