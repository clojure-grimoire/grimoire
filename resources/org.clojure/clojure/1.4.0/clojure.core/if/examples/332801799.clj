;; Be aware that the only two values considered "logical false" in Clojure
;; are nil and false, where Clojure's "false" is the Java value Boolean/FALSE
;; under the hood.  Everything else is "logical true".  Particularly surprising
;; may be that the Java Object with class Boolean and value (Boolean. false) is
;; considered logical true.

;; This notion of logical true and logical false holds for at least the following
;; conditional statements in Clojure: if, cond, when, if-let, when-let.
;; It also applies to functions like filter, remove, and others that use
;; these conditional statements in their implementation.

;; nil and false are logical false
user=> (if nil "logical true" "logical false")
"logical false"
user=> (if false "logical true" "logical false")
"logical false"
;; Boolean/FALSE is how Clojure's "false" is represented internally.
user=> (if Boolean/FALSE "logical true" "logical false")
"logical false"

;; Everything else that is the value of the condition, including numbers,
;; characters, strings, vectors, maps, _and_ a freshly constructed Boolean class
;; object (Boolean. false), is logical true.

user=> (if 1 "logical true" "logical false")
"logical true"
;; A vector containing nil is not the same as nil.
user=> (if [nil] "logical true" "logical false")
"logical true"
user=> (if (first [nil]) "logical true" "logical false")
"logical false"

;; Bad idea even in Java.  See below for more details.
user=> (if (Boolean. false) "logical true" "logical false")
"logical true"

;; Java documentation itself warns:
;; Note: It is rarely appropriate to use this constructor. Unless a new instance
;; is required, the static factory valueOf(boolean) is generally a better choice.
;; It is likely to yield significantly better space and time performance.

;; (boolean x) converts a value to a primitive boolean.  It converts nil, false,
;; and (Boolean. false) to primitive false.
user=> (if (boolean (Boolean. false)) "logical true" "logical false")
"logical false"

;; (Boolean/valueOf <val>) is similar:
user=> (if (Boolean/valueOf (Boolean. false)) "logical true" "logical false")
"logical false"
