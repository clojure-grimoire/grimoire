(ns http)

(defn post [url]
  {:body "Hello world"})

(ns app
  (:require [clojure.test :refer [deftest is run-tests]]))

(deftest is-a-macro
  (with-redefs [http/post (fn [url] {:body "Goodbye world"})]
    (is (= {:body "Goodbye world"} (http/post "http://service.com/greet")))))

(run-tests) ;; test is passing