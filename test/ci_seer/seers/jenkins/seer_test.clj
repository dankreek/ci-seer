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
        (is (= 11 (count jobs)))
        (letfn [(job [name] (seers/find-job-by-name jobs name))]
          (is (:running (job "job1")))
          (is (not (:running (job "job2"))))
          (is (= :failing (:status (job "job3"))))
          (is (= :unstable (:status (job "job4"))))
          (is (= :pending (:status (job "job5"))))
          (is (= :disabled (:status (job "job6"))))
          (is (= :aborted (:status (job "job7"))))
          (is (= :notbuilt (:status (job "job8")))))))))
