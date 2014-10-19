(ns ci-seer.ci-seers.jenkins.jenkins-test
  (:import (java.net URL))
  (:require [clojure.test :refer :all]
            [ci-seer.ci-seers.core :as seers]
            [ci-seer.ci-seers.jenkins :as jenkins]
            [clj-time.core :as time]
            [schema.test :as schema]))

(use-fixtures :once schema/validate-schemas)

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
    (is (= (seers/supported-system jenkins/seer) :jenkins)))

  (with-redefs [jenkins/fetch-view-payload mock-fetch-view-payload]
    (let [jobs (seers/get-jobs-in-folder jenkins/seer mock-context nil)]
      (testing "Parsing JSON payload into a jobs list."
        (is (= 12 (count jobs)))
        (letfn [(get-job [name] (seers/find-job-by-name jobs name))]
          (testing "Parsing job statuses."
            (doseq [[expected-status job-name] [[:passing  "job1"]
                                                [:disabled "job2"]
                                                [:passing  "job3"]
                                                [:aborted  "job4"]
                                                [:pending  "job5"]
                                                [:failing  "job6"]
                                                [:unstable "job7"]
                                                [:passing  "job8"]
                                                [:disabled "job10"]
                                                [:passing  "job11"]
                                                [:notbuilt "job12"]]]
              (is (= expected-status (:status (get-job job-name))))))

          (testing "Parsing :label attribute."
            (doseq [[label name] [["Job 1"       "job1"]
                                  ["Job #2"      "job2"]
                                  ["Nice Jorb 3" "job3"]
                                  ["job4"        "job4"]]]
              (is (= label (:label (get-job name))))))

          (testing "A currently running job parses correctly."
            (let [job1 (get-job "job1")
                  job2 (get-job "job2")
                  job11 (get-job "job11")]
              (is (not (nil? (:running-job job1))))
              (is (= (time/date-time 2014 3 26 5 5 2)
                     (get-in job1 [:running-job :start-time])))
              (is (nil? (:running-job job2)))

              (testing "Estimated duration is parsed correctly."
                (is (= 13638 (get-in job1 [:running-job :estimated-duration])))
                (is (nil? (get-in job11 [:running-job :estimated-duration]))))

              (testing "In-queue is parsed correctly."
                (is (:in-build-queue job1))
                (is (not (:in-build-queue job2)))))))))))

