(ns ci-seer.services.pr-seer.core-test
  (:require [clojure.test :refer :all]
            [ci-seer.services.pr-seer.core :refer :all]
            [ci-seer.pr-seers.core :as pr-core]
            [schema.test :as schema]))

(use-fixtures :once schema/validate-schemas)

(def test-service-1
  (reify pr-core/PrSeer
    (supported-system [_] :test-1)
    (config->context [_ _] {:i-am "thing1"})
    (get-pr-data [_ _] [])))

(def test-service-2
  (reify pr-core/PrSeer
    (supported-system [_] :test-2)
    (config->context [_ _] {:i-am "thing2"})
    (get-pr-data [_ _] [])))

(def test-seers ["ci-seer.services.pr-seer.core-test/test-service-1"
                 "ci-seer.services.pr-seer.core-test/test-service-2"])

(deftest create-service-state
  (testing "config->context creates a proper context map"
    (let [context (config->context {:pr-seers test-seers
                                    :test-1   {:config "blah"}
                                    :test-2   {:config "blah"}})]
      (is (= context {:test-1 {:seer test-service-1
                               :context {:i-am "thing1"}}
                      :test-2 {:seer test-service-2
                               :context {:i-am "thing2"}}})))))

