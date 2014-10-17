(ns ci-seer.pr-seers.github.github-test
  (:require [clojure.test :refer :all]
            [schema.test :as schema]
            [ci-seer.pr-seers.core :as core]
            [ci-seer.pr-seers.github :as github]
            [ci-seer.pr-seers.github.pulls-mock :as pulls-mock]
            [clj-time.coerce :as ctime]
            [tentacles.pulls :as pulls]))

(use-fixtures :once schema/validate-schemas)

(deftest supported-system-test
  (testing "The correct keyword is returned by supported-system"
    (is (= :github (core/supported-system github/pr-seer)))))

(deftest outstanding-prs
  (testing "Outstanding PRs correctly measured."
    (with-redefs [pulls/pulls pulls-mock/pulls-mock-with-prs]
      (let [pr-data (core/get-pr-data github/pr-seer "" "" {})]
        (is (= 2 (:num-prs pr-data)))
        (is (= (ctime/from-string "2014-10-15T19:02:14Z")
               (:oldest-pr pr-data)))))))

(deftest no-outstanding-prs
  (testing "No outstanding PRs returns correct result."
    (with-redefs [pulls/pulls pulls-mock/pulls-mock-with-no-prs]
      (let [pr-data (core/get-pr-data github/pr-seer "" "" {})]
        (println pr-data)
        (is (= 0 (:num-prs pr-data)))
        (is (nil? (:oldest-pr pr-data)))))))

(deftest auth-key
  (testing "Correct oauth maps is produced"
    (is (= {:oauth-token "TOKEN"}
           (github/get-auth-key {:oauth-token "TOKEN"}))))
  (testing "No map is produced with no key"
    (is (nil? (github/get-auth-key nil)))
    (is (nil? (github/get-auth-key {:something-invalid "invalid"})))
    (is (nil? (github/get-auth-key ["something" "not" "a" "map"])))))
