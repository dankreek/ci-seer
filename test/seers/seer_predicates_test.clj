(ns seers.seer-predicates-test
  (:import (java.net URL))
  (:require [clojure.test :refer :all]
            [seers.seer-protocol :refer :all]))

(deftest predicates-return-proper-values
  (testing "server? properly identified"
    (is (server? {:url  (URL. "https://test.me")
                  :type :jenkins}))
    (is (not (server? {})))
    (is (not (server? {:url "http://not.a.url.obj"
                       :type :jenkins})))
    (is (not (server? {:poop "poop"
                       :tinkle "tinkle"}))))
  
  (testing "status? properly identified"
    (is (status? :passing))
    (is (status? :failing))
    (is (status? :running))
    (is (not (status? :somethingelse)))))
