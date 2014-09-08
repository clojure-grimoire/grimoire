(deftype ArrayChunk [^clojure.core.ArrayManager am arr ^int off ^int end]
  
  clojure.lang.Indexed
  (nth [_ i] (.aget am arr (+ off i)))
  
  (count [_] (- end off))

  clojure.lang.IChunk
  (dropFirst [_]
    (if (= off end)
      (throw (IllegalStateException. "dropFirst of empty chunk"))
      (new ArrayChunk am arr (inc off) end)))
  
  (reduce [_ f init]
    (loop [ret init i off]
      (if (< i end)
        (recur (f ret (.aget am arr i)) (inc i))
        ret)))
  )