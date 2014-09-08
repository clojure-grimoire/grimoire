;; The key concept to understand here is that transients are 
;; not meant to be `bashed in place`; always use the value 
;; returned by either assoc! or other functions that operate
;; on transients.

(defn merge2
  "An example implementation of `merge` using transients."
  [x y]
  (persistent! (reduce
                (fn [res [k v]] (assoc! res k v))
                (transient x)
                y)))

;; Why always use the return value, and not the original?  Because the return
;; value might be a different object than the original.  The implementation
;; of Clojure transients in some cases changes the internal representation
;; of a transient collection (e.g. when it reaches a certain size).  In such
;; cases, if you continue to try modifying the original object, the results
;; will be incorrect.

;; Think of transients like persistent collections in how you write code to
;; update them, except unlike persistent collections, the original collection
;; you passed in should be treated as having an undefined value.  Only the return
;; value is predictable.