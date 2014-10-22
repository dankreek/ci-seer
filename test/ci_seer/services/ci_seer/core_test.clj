(ns ci-seer.services.ci-seer.core-test
  (:import (java.net URL MalformedURLException)
           (java.io FileNotFoundException))
  (:require [clojure.test :refer :all]
            [ci-seer.services.ci-seer.core :refer :all]
            [ci-seer.ci-seers.core :as seers]
            [ci-seer.ci-seers.jenkins :as jenkins]
            [schema.test :as schema]))

(use-fixtures :once schema/validate-schemas)

(def test-url "https://www.dontcopythat.floppy")
(def config-test-type "jenkins")
(def test-folders ["clojure"])
(def test-jobs ["ajob" "anotherjob"])
(def config-seer-list ["ci-seer.ci-seers.jenkins/seer"])

(def good-test-config
  {:seers   config-seer-list
   :servers [{:type    config-test-type
              :url     test-url
              :folders test-folders
              :jobs    test-jobs}]})

(def invalid-url-config
  {:seers   config-seer-list
   :servers [{:type    config-test-type
              :url     "invalid url"}]})

(def invalid-seer-name
  {:seers   ["some-bullcrap/seer"]
   :servers [{:type    config-test-type
              :url     "invalid url"}]})

(def context-seers {:jenkins jenkins/seer})
(def context-seer-type :jenkins)

(def good-test-context
  {:seers   context-seers
   :servers [{:type    context-seer-type
              :url     (URL. test-url)
              :jobs    test-jobs
              :folders test-folders
              :jobs-status (atom {})}]})

(def invalid-protocol-context
  {:seers   context-seers
   :servers [{:type    context-seer-type
              :url     (URL. "ftp://test.bunk")
              :jobs    test-jobs
              :jobs-status (atom {})}]})

(def no-jobs-test-context
  {:seers   context-seers
   :servers [{:type    context-seer-type
              :url     (URL. test-url)
              :jobs-status (atom {})}]})

(deftest testing-config->context
  (testing "A proper config is tranasformed"
    (let [context (config->context good-test-config)
          [server-context] (:servers context)]
      (is (= (keyword config-test-type) (:type server-context)))
      (is (= test-url (str (URL. test-url))))
      (is (= test-jobs (:jobs server-context)))
      (is (= test-folders (:folders server-context)))
      (is (satisfies? seers/CiSeer
                      (get-in context [:seers (keyword config-test-type)])))))

  (testing "Invalid seer"
    (is (thrown-with-msg?
          IllegalStateException
          #"The identifier 'some-bullcrap/seer' could not be found."
          (config->context invalid-seer-name))))

  (testing "Invalid URL throws error."
    (is (thrown-with-msg?
          IllegalStateException
          #"Could not parse the URL"
          (config->context invalid-url-config)))))

(deftest testing-validate-context
  (testing "A proper context is validated"
    (is (= good-test-context
           (validate-context good-test-context))))

  (testing "An invalid protocol for a URL was provided."
    (is (thrown-with-msg?
          IllegalStateException
          #"The ftp protocol is not supported"
          (validate-context invalid-protocol-context))))

  (testing "No jobs or folders results in an error."
    (is (thrown-with-msg?
          IllegalStateException
          #"A list of jobs or folders must be provided"
          (validate-context no-jobs-test-context)))))
