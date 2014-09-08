;; Implementing factorial using multimethods Note that factorial-like function 
;; is best implemented using `recur` for enable tail-call optimization to avoid 
;; stack overflow error. This is a only a demonstration of clojure's multimethod

;; identity form returns the same value passed
(defmulti factorial identity)

(defmethod factorial 0 [_]  1)
(defmethod factorial :default [num] 
    (* num (factorial (dec num))))

(factorial 0) ; => 1
(factorial 1) ; => 1
(factorial 3) ; => 6
(factorial 7) ; => 5040