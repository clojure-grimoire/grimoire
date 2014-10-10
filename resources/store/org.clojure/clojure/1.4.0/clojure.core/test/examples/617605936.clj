(defn my-function
  "this function adds two numbers"
  {:test #(do
            (assert (= (my-function 2 3) 5))
            (assert (= (my-function 4 4) 8)))}
  ([x y] (+ x y)))

(test #'my-function)  ;equal to (test (var my-function))
=> :ok

-----------------------------------------------------------------------

(defn my-function
  "this function adds two numbers"
  {:test #(do
            (assert (= (my-function 2 3) 5))
            (assert (= (my-function 99 4) 8)))}
  ([x y] (+ x y)))

(test #'my-function)
=> java.lang.AssertionError: Assert failed: (= (my-function 99 4) 8) (NO_SOURCE_FILE:0

---------------------------------------------------------------------------

(defn my-function
  "this function adds two numbers"
  ([x y] (+ x y)))

(test #'my-function)
=> :no-test