(def arr-impl
  '(internal-reduce
       [a-seq f val]
       (let [arr (.array a-seq)]
         (loop [i (.index a-seq)
                val val]
           (if (< i (alength arr))
             (let [ret (f val (aget arr i))]
                (if (reduced? ret)
                  @ret
                  (recur (inc i) ret)))
             val)))))