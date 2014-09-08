;; Convert number into bits:
user=> (defn bits [n s]
         (take s
               (map
                 (fn [i] (bit-and 0x01 i))
                 (iterate
                   (fn [i] (bit-shift-right i 1))
                   n))))
#'user/bits

user=> (map (fn [n] (bits n 3)) (range 8))
((0 0 0) (1 0 0) (0 1 0) (1 1 0) (0 0 1) (1 0 1) (0 1 1) (1 1 1))
