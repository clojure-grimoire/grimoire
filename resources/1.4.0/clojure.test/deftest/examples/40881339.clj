;failure test example

;there is nesting, so when a leaf test fails so does its parents, in this example 2 tests fail, though there was only one real error.

(ns testing)
(use 'clojure.test)


(deftest addition
  (is (= 4 (+ 2 2)))
  (is (= 7 (+ 3 4))))
=> #'testing/addition

(deftest subtraction
  (is (= 1 (- 4 3)))
  (is (= 6 (- 7 4))))           ;error
=> #'testing/subtraction

;composing tests
(deftest arithmetic
  (addition)
  (subtraction))
=> #'testing/arithmetic

(run-tests 'testing)

=> Testing testing

FAIL in (arithmetic subtraction) (NO_SOURCE_FILE:669)
expected: (= 6 (- 7 4))
  actual: (not (= 6 3))

FAIL in (subtraction) (NO_SOURCE_FILE:669)
expected: (= 6 (- 7 4))
  actual: (not (= 6 3))

Ran 6 tests containing 10 assertions.
2 failures, 0 errors.
{:type :summary, :test 6, :pass 8, :fail 2, :error 0}