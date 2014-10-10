;; mapping over a hash-map applies (into) first. 
;; need to use functions that deal with arrays (fn [[key val]] ...)
(map pprint {:key :val :key1 :val1})
([:key :val]
[:key1 :val1]
nil nil)

;;above, the pprint output appears to be part of the return value but it's not:
(hash-set (map pprint {:key :val :key1 :val1}))
[:key :val]
[:key1 :val1]
#{(nil nil)}

(map second {:key :val :key1 :val1})
(:val :val1)