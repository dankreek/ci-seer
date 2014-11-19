(ns ci-seer.pr-seers.github.github-test
  (:require [clojure.test :refer :all]
            [schema.test :as schema]
            [ci-seer.pr-seers.core :as core]
            [ci-seer.pr-seers.github :as github]
            [ci-seer.pr-seers.github.pulls-mock :as pulls-mock]
            [clj-time.coerce :as ctime]
            [tentacles.pulls :as pulls]
            [slingshot.test :refer :all]))

(use-fixtures :once schema/validate-schemas)

(def repo-list
  ["user/repo1" "user/repo2"])

(deftest supported-system-test
  (testing "The correct keyword is returned by supported-system"
    (is (= :github (core/supported-system github/pr-seer)))))

(deftest parse-config-into-context
  (testing "Parsing a simple repository list."
    (is (= (github/github-config->context repo-list)
           [{:user "user", :repo "repo1"}
            {:user "user", :repo "repo2"}]))

    (is (= (github/github-config->context [])
           [])))

  (testing "Parsing a map with an OAuth token and a repo list."
    (is (= (github/github-config->context {:oauth-token "ABCDEFG"
                                           :repos       repo-list})
           [{:user "user", :repo "repo1", :oauth-token "ABCDEFG"}
            {:user "user", :repo "repo2", :oauth-token "ABCDEFG"}])))

  (testing "Invalid config results in an exception"
    (is (thrown+? [:type    :ci-seer.pr-seers.github/invalid-config
                   :message "An invalid configuration was provided."]
                  (github/github-config->context {:poop "pee"})))))

(def mock-context [{:repo "blah"
                    :user "repo"}])

(deftest outstanding-prs
  (testing "Outstanding PRs correctly measured."
    (with-redefs [pulls/pulls pulls-mock/pulls-mock-with-prs]
      (let [pr-data (first (core/get-pr-data github/pr-seer mock-context))]
        (is (= 2 (:num-prs pr-data)))
        (is (= (ctime/from-string "2014-10-15T19:02:14Z")
               (:oldest-pr pr-data)))))))

(deftest no-outstanding-prs
  (testing "No outstanding PRs returns correct result."
    (with-redefs [pulls/pulls pulls-mock/pulls-mock-with-no-prs]
      (let [pr-data (first (core/get-pr-data github/pr-seer mock-context))]
        (is (= 0 (:num-prs pr-data)))
        (is (nil? (:oldest-pr pr-data)))))))

