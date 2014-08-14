;; let is a Clojure special form, a fundamental building block of the language.
;;
;; In addition to parameters passed to functions, let provides a way to create
;; lexical bindings of data structures to symbols. The binding, and therefore 
;; the ability to resolve the binding, is available only within the lexical 
;; context of the let. 
;; 
;; let uses pairs in a vector for each binding you'd like to make and the value 
;; of the let is the value of the last expression to be evaluated. let also 
;; allows for destructuring which is a way to bind symbols to only part of a 
;; collection.

;; A basic use for a let:
user=> (let [x 1] 
         x)
1

;; Note that the binding for the symbol y won't exist outside of the let:
user=> (let [y 1] 
         y)
1
user=> (prn y)
java.lang.Exception: Unable to resolve symbol: y in this context (NO_SOURCE_FILE:7)

;; Another valid use of let:
user=> (let [a 1 b 2] 
         (+ a b))
3

;; The forms in the vector can be more complex (this example also uses
;; the thread macro):
user=> (let [c (+ 1 2)
             [d e] [5 6]] 
         (-> (+ d e) (- c)))
8

;; The bindings for let need not match up (note the result is a numeric
;; type called a ratio):
user=> (let [[g h] [1 2 3]] 
         (/ g h))
1/2

;; From http://clojure-examples.appspot.com/clojure.core/let with permission.