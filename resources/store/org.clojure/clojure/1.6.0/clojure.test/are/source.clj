(defmacro are
  "Checks multiple assertions with a template expression.
  See clojure.template/do-template for an explanation of
  templates.

  Example: (are [x y] (= x y)  
                2 (+ 1 1)
                4 (* 2 2))
  Expands to: 
           (do (is (= 2 (+ 1 1)))
               (is (= 4 (* 2 2))))

  Note: This breaks some reporting features, such as line numbers."
  {:added "1.1"}
  [argv expr & args]
  (if (or
       ;; (are [] true) is meaningless but ok
       (and (empty? argv) (empty? args))
       ;; Catch wrong number of args
       (and (pos? (count argv))
            (pos? (count args))
            (zero? (mod (count args) (count argv)))))
    `(temp/do-template ~argv (is ~expr) ~@args)
    (throw (IllegalArgumentException. "The number of args doesn't match are's argv."))))