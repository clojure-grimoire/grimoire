;; As seen on http://clojure.org/transients
;; array is initially made transient, modified then
;; finally made persistent.

(defn vrange2 [n]
  (loop [i 0 v (transient [])]
    (if (< i n)
      (recur (inc i) (conj! v i))
      (persistent! v))))

user=> (vrange2 10)
[0 1 2 3 4 5 6 7 8 9]