; See https://clojure.github.io/clojure/clojure.test-api.html for details

; my-test-fixture will be passed a fn that will call all your tests 
; (e.g. test-using-db).  Here you perform any required setup 
; (e.g. create-db), then call the passed function f, then perform 
; any required teardown (e.g. destroy-db).
(defn my-test-fixture [f]
        (create-db)
        (f)
        (destroy-db))

; Here we register my-test-fixture to be called once, wrapping ALL tests 
; in the namespace
(use-fixtures :once my-test-fixture)
 
; This is a regular test function, which is to be wrapped using my-test-fixture
(deftest test-using-db
  (is ... 
))