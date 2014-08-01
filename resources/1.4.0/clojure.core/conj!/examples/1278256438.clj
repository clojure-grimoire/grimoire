;; As seen on http://clojure.org/transients
;; array is initially made transient, modified then
;; finally made persistent.

;; Note: This example correctly always uses the return value of conj! for
;; future modifications, not the original value of v.  See assoc! examples
;; for some discussion of why this is important.

(defn vrange2 [n]
  (loop [i 0 v (transient [])]
    (if (< i n)
      (recur (inc i) (conj! v i))
      (persistent! v))))

user=> (vrange2 10)
[0 1 2 3 4 5 6 7 8 9]