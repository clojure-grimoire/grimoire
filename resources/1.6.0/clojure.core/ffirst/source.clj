(def
 ^{:doc "Same as (first (first x))"
   :arglists '([x])
   :added "1.0"
   :static true}
 ffirst (fn ^:static ffirst [x] (first (first x))))