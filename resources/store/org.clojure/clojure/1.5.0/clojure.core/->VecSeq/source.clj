(deftype VecSeq [^clojure.core.ArrayManager am ^clojure.core.IVecImpl vec anode ^int i ^int offset] 
  :no-print true

  clojure.core.protocols.InternalReduce
  (internal-reduce
   [_ f val]
   (loop [result val
          aidx offset]
     (if (< aidx (count vec))
       (let [node (.arrayFor vec aidx)
             result (loop [result result
                           node-idx (bit-and 0x1f aidx)]
                      (if (< node-idx (.alength am node))
                        (recur (f result (.aget am node node-idx)) (inc node-idx))
                        result))]
         (recur result (bit-and 0xffe0 (+ aidx 32))))
       result)))
  
  clojure.lang.ISeq
  (first [_] (.aget am anode offset))
  (next [this] 
    (if (< (inc offset) (.alength am anode))
      (new VecSeq am vec anode i (inc offset))
      (.chunkedNext this)))
  (more [this]
    (let [s (.next this)]
      (or s (clojure.lang.PersistentList/EMPTY))))
  (cons [this o]
    (clojure.lang.Cons. o this))
  (count [this]
    (loop [i 1
           s (next this)]
      (if s
        (if (instance? clojure.lang.Counted s)
          (+ i (.count s))
          (recur (inc i) (next s)))
        i)))
  (equiv [this o]
    (cond
     (identical? this o) true
     (or (instance? clojure.lang.Sequential o) (instance? java.util.List o))
     (loop [me this
            you (seq o)]
       (if (nil? me)
         (nil? you)
         (and (clojure.lang.Util/equiv (first me) (first you))
              (recur (next me) (next you)))))
     :else false))
  (empty [_]
    clojure.lang.PersistentList/EMPTY)


  clojure.lang.Seqable
  (seq [this] this)

  clojure.lang.IChunkedSeq
  (chunkedFirst [_] (ArrayChunk. am anode offset (.alength am anode)))
  (chunkedNext [_] 
   (let [nexti (+ i (.alength am anode))]
     (when (< nexti (count vec))
       (new VecSeq am vec (.arrayFor vec nexti) nexti 0))))
  (chunkedMore [this]
    (let [s (.chunkedNext this)]
      (or s (clojure.lang.PersistentList/EMPTY)))))