(ns seers.jenkins.seer-test
  (:require [clojure.test :refer :all]
            [schema.core :as schema]
            [seers.core :as seers]
            [seers.jenkins :as jenkins]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utilities

(defn jenkins-view-json []
  (slurp "./dev-resources/jenkins_view.json"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Tests

(deftest jenkins-seer-test
  (testing "Properly indicates that Jenkins is supported"
    (is (seers/supports? jenkins/seer :jenkins))))

(deftest jenkins-seer-core-test
  (testing "Parsing Jenkins view JSON"
    (is (= 3 (count (:jobs (jenkins/parse-view-payload
                             (jenkins-view-json))))))))
