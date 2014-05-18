(ns ci-seer.seers.jenkins.seer-test
  (:import (java.net URL))
  (:require [clojure.test :refer :all]
            [schema.core :as schema]
            [ci-seer.seers.core :as seers]
            [ci-seer.seers.jenkins :as jenkins]
            [clj-time.core :as time]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Utilities

(def mock-view-payload
  "./dev-resources/jenkins_view.json")

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
        (is (= 12 (count jobs)))
        (letfn [(get-job [name] (seers/find-job-by-name jobs name))]
          (testing "Parsing job statuses."
            (is (= :passing  (:status (get-job "job1"))))
            (is (= :disabled (:status (get-job "job2"))))
            (is (= :passing  (:status (get-job "job3"))))
            (is (= :aborted  (:status (get-job "job4"))))
            (is (= :pending  (:status (get-job "job5"))))
            (is (= :failing  (:status (get-job "job6"))))
            (is (= :unstable (:status (get-job "job7"))))
            (is (= :passing  (:status (get-job "job8"))))
            (is (= :passing  (:status (get-job "job9"))))
            (is (= :disabled (:status (get-job "job10"))))
            (is (= :passing  (:status (get-job "job11"))))
            (is (= :notbuilt (:status (get-job "job12")))))

          (testing "Parsing :label attribute."
            (is (= "job1" (:label (get-job "job1"))))
            (is (= "job2" (:label (get-job "job2"))))
            (is (= "job3" (:label (get-job "job3"))))
            (is (= "job4" (:label (get-job "job4"))))
            (is (= "job5" (:label (get-job "job5"))))
            (is (= "job6" (:label (get-job "job6"))))
            (is (= "job7" (:label (get-job "job7"))))
            (is (= "job8" (:label (get-job "job8"))))
            (is (= "job9" (:label (get-job "job9"))))
            (is (= "job10" (:label (get-job "job10"))))
            (is (= "job11" (:label (get-job "job11"))))
            (is (= "job12" (:label (get-job "job12")))))

          (testing "A currently running job parses correctly."
            (let [job1 (get-job "job1")
                  job2 (get-job "job2")]
              (is (not (nil? (:running-job job1))))
              (is (= (time/date-time 2014 3 26 5 5 2)
                     (get-in job1 [:running-job :start-time])))
              (is (= 13638
                     (get-in job1 [:running-job :estimated-duration])))
              (is (nil? (:running-job job2))))))))))

