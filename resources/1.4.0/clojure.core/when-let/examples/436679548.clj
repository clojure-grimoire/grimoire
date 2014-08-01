;; Very useful when working with sequences. Capturing the return value 
;; of `seq` brings a performance gain in subsequent `first`/`rest`/`next`
;; calls. Also the block is guarded by `nil` punning.

(defn drop-one
  [coll]
  (when-let [s (seq coll)]
    (rest s)))

user=> (drop-one [1 2 3])
(2 3)
user=> (drop-one [])
nil
