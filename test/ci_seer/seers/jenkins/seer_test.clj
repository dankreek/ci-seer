(ns ci-seer.seers.jenkins.seer-test
  (:import (java.net URL))
  (:require [clojure.test :refer :all]
            [schema.core :as schema]
            [ci-seer.seers.core :as seers]
            [ci-seer.seers.jenkins :as jenkins]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utilities

(def mock-view-payload "./dev-resources/jenkins_view.json")

(defn mock-fetch-view-payload [_ _]
  (slurp mock-view-payload))

(def mock-context {:url (URL. "http://localhost")
                   :type :jenkins})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Tests

(deftest jenkins-seer-test
  (testing "Properly indicates that Jenkins is supported by this seer."
    (is (seers/supports? jenkins/seer :jenkins)))

  (with-redefs [jenkins/fetch-view-payload mock-fetch-view-payload]
    (let [jobs (seers/get-jobs-in-folder jenkins/seer mock-context nil)]
      (testing "Parsing JSON payload into a jobs list."
        (is (= 11 (count jobs)))))))
