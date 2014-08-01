;with test is the same as using {:test #((is...)(is...))} in the meta data of the function.

(:use 'clojure.test)

(with-test
    (defn my-function [x y]
      (+ x y))
  (is (= 4 (my-function 2 2)))
  (is (= 7 (my-function 3 4))))

(test #'my-function)            ;(test (var my-function))
=> :ok