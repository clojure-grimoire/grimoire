(defn hash-combine [x y] 
  (clojure.lang.Util/hashCombine x (clojure.lang.Util/hash y)))