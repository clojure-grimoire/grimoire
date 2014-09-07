(def arr-impl
  '(internal-reduce
       [a-seq f val]
       (let [arr (.array a-seq)]
         (loop [i (.index a-seq)
                val val]
           (if (< i (alength arr))
             (recur (inc i) (f val (aget arr i)))
             val)))))