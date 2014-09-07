(deftype Vec [^clojure.core.ArrayManager am ^int cnt ^int shift ^clojure.core.VecNode root tail _meta]
  Object
  (equals [this o]
    (cond 
     (identical? this o) true
     (or (instance? clojure.lang.IPersistentVector o) (instance? java.util.RandomAccess o))
       (and (= cnt (count o))
            (loop [i (int 0)]
              (cond
               (= i cnt) true
               (.equals (.nth this i) (nth o i)) (recur (inc i))
               :else false)))
     (or (instance? clojure.lang.Sequential o) (instance? java.util.List o))
       (if-let [st (seq this)]
         (.equals st (seq o))
         (nil? (seq o)))
     :else false))

  ;todo - cache
  (hashCode [this]
    (loop [hash (int 1) i (int 0)]
      (if (= i cnt)
        hash
        (let [val (.nth this i)]
          (recur (unchecked-add-int (unchecked-multiply-int 31 hash) 
                                (clojure.lang.Util/hash val)) 
                 (inc i))))))

  ;todo - cache
  clojure.lang.IHashEq
  (hasheq [this]
    (Murmur3/hashOrdered this))

  clojure.lang.Counted
  (count [_] cnt)

  clojure.lang.IMeta
  (meta [_] _meta)

  clojure.lang.IObj
  (withMeta [_ m] (new Vec am cnt shift root tail m))

  clojure.lang.Indexed
  (nth [this i]
    (let [a (.arrayFor this i)]
      (.aget am a (bit-and i (int 0x1f)))))
  (nth [this i not-found]
       (let [z (int 0)]
         (if (and (>= i z) (< i (.count this)))
           (.nth this i)
           not-found)))

  clojure.lang.IPersistentCollection
  (cons [this val]
     (if (< (- cnt (.tailoff this)) (int 32))
      (let [new-tail (.array am (inc (.alength am tail)))]
        (System/arraycopy tail 0 new-tail 0 (.alength am tail))
        (.aset am new-tail (.alength am tail) val)
        (new Vec am (inc cnt) shift root new-tail (meta this)))
      (let [tail-node (VecNode. (.edit root) tail)] 
        (if (> (bit-shift-right cnt (int 5)) (bit-shift-left (int 1) shift)) ;overflow root?
          (let [new-root (VecNode. (.edit root) (object-array 32))]
            (doto ^objects (.arr new-root)
              (aset 0 root)
              (aset 1 (.newPath this (.edit root) shift tail-node)))
            (new Vec am (inc cnt) (+ shift (int 5)) new-root (let [tl (.array am 1)] (.aset am  tl 0 val) tl) (meta this)))
          (new Vec am (inc cnt) shift (.pushTail this shift root tail-node) 
                 (let [tl (.array am 1)] (.aset am  tl 0 val) tl) (meta this))))))

  (empty [_] (new Vec am 0 5 EMPTY-NODE (.array am 0) nil))                             
  (equiv [this o]
    (cond 
     (or (instance? clojure.lang.IPersistentVector o) (instance? java.util.RandomAccess o))
       (and (= cnt (count o))
            (loop [i (int 0)]
              (cond
               (= i cnt) true
               (= (.nth this i) (nth o i)) (recur (inc i))
               :else false)))
     (or (instance? clojure.lang.Sequential o) (instance? java.util.List o))
       (clojure.lang.Util/equiv (seq this) (seq o))
     :else false))

  clojure.lang.IPersistentStack
  (peek [this]
    (when (> cnt (int 0)) 
      (.nth this (dec cnt))))

  (pop [this]
   (cond
    (zero? cnt) 
      (throw (IllegalStateException. "Can't pop empty vector"))
    (= 1 cnt) 
      (new Vec am 0 5 EMPTY-NODE (.array am 0) (meta this))
    (> (- cnt (.tailoff this)) 1)
      (let [new-tail (.array am (dec (.alength am tail)))]
        (System/arraycopy tail 0 new-tail 0 (.alength am new-tail))
        (new Vec am (dec cnt) shift root new-tail (meta this)))
    :else
      (let [new-tail (.arrayFor this (- cnt 2))
            new-root ^clojure.core.VecNode (.popTail this shift root)]
        (cond
         (nil? new-root) 
           (new Vec am (dec cnt) shift EMPTY-NODE new-tail (meta this))
         (and (> shift 5) (nil? (aget ^objects (.arr new-root) 1)))
           (new Vec am (dec cnt) (- shift 5) (aget ^objects (.arr new-root) 0) new-tail (meta this))
         :else
           (new Vec am (dec cnt) shift new-root new-tail (meta this))))))

  clojure.lang.IPersistentVector
  (assocN [this i val]
    (cond 
     (and (<= (int 0) i) (< i cnt))
       (if (>= i (.tailoff this))
         (let [new-tail (.array am (.alength am tail))]
           (System/arraycopy tail 0 new-tail 0 (.alength am tail))
           (.aset am new-tail (bit-and i (int 0x1f)) val)
           (new Vec am cnt shift root new-tail (meta this)))
         (new Vec am cnt shift (.doAssoc this shift root i val) tail (meta this)))
     (= i cnt) (.cons this val)
     :else (throw (IndexOutOfBoundsException.))))
  
  clojure.lang.Reversible
  (rseq [this]
        (if (> (.count this) 0)
          (clojure.lang.APersistentVector$RSeq. this (dec (.count this)))
          nil))
  
  clojure.lang.Associative
  (assoc [this k v]
    (if (clojure.lang.Util/isInteger k)
      (.assocN this k v)
      (throw (IllegalArgumentException. "Key must be integer"))))
  (containsKey [this k]
    (and (clojure.lang.Util/isInteger k)
         (<= 0 (int k))
         (< (int k) cnt)))
  (entryAt [this k]
    (if (.containsKey this k)
      (clojure.lang.MapEntry. k (.nth this (int k)))
      nil))

  clojure.lang.ILookup
  (valAt [this k not-found]
    (if (clojure.lang.Util/isInteger k)
      (let [i (int k)]
        (if (and (>= i 0) (< i cnt))
          (.nth this i)
          not-found))
      not-found))

  (valAt [this k] (.valAt this k nil))

  clojure.lang.IFn
  (invoke [this k]
    (if (clojure.lang.Util/isInteger k)
      (let [i (int k)]
        (if (and (>= i 0) (< i cnt))
          (.nth this i)
          (throw (IndexOutOfBoundsException.))))
      (throw (IllegalArgumentException. "Key must be integer"))))

  
  clojure.lang.Seqable
  (seq [this] 
    (if (zero? cnt) 
      nil
      (VecSeq. am this (.arrayFor this 0) 0 0)))

  clojure.lang.Sequential ;marker, no methods

  clojure.core.IVecImpl
  (tailoff [_] 
    (- cnt (.alength am tail)))

  (arrayFor [this i]
    (if (and  (<= (int 0) i) (< i cnt))
      (if (>= i (.tailoff this))
        tail
        (loop [node root level shift]
          (if (zero? level)
            (.arr node)
            (recur (aget ^objects (.arr node) (bit-and (bit-shift-right i level) (int 0x1f))) 
                   (- level (int 5))))))
      (throw (IndexOutOfBoundsException.))))

  (pushTail [this level parent tailnode]
    (let [subidx (bit-and (bit-shift-right (dec cnt) level) (int 0x1f))
          parent ^clojure.core.VecNode parent
          ret (VecNode. (.edit parent) (aclone ^objects (.arr parent)))
          node-to-insert (if (= level (int 5))
                           tailnode
                           (let [child (aget ^objects (.arr parent) subidx)]
                             (if child
                               (.pushTail this (- level (int 5)) child tailnode)
                               (.newPath this (.edit root) (- level (int 5)) tailnode))))]
      (aset ^objects (.arr ret) subidx node-to-insert)
      ret))

  (popTail [this level node]
    (let [node ^clojure.core.VecNode node
          subidx (bit-and (bit-shift-right (- cnt (int 2)) level) (int 0x1f))]
      (cond
       (> level 5) 
         (let [new-child (.popTail this (- level 5) (aget ^objects (.arr node) subidx))]
           (if (and (nil? new-child) (zero? subidx))
             nil
             (let [arr (aclone ^objects (.arr node))]
               (aset arr subidx new-child)
               (VecNode. (.edit root) arr))))
       (zero? subidx) nil
       :else (let [arr (aclone ^objects (.arr node))]
               (aset arr subidx nil)
               (VecNode. (.edit root) arr)))))

  (newPath [this edit ^int level node]
    (if (zero? level)
      node
      (let [ret (VecNode. edit (object-array 32))]
        (aset ^objects (.arr ret) 0 (.newPath this edit (- level (int 5)) node))
        ret)))

  (doAssoc [this level node i val]
    (let [node ^clojure.core.VecNode node]       
      (if (zero? level)
        ;on this branch, array will need val type
        (let [arr (.aclone am (.arr node))]
          (.aset am arr (bit-and i (int 0x1f)) val)
          (VecNode. (.edit node) arr))
        (let [arr (aclone ^objects (.arr node))
              subidx (bit-and (bit-shift-right i level) (int 0x1f))]
          (aset arr subidx (.doAssoc this (- level (int 5)) (aget arr subidx) i val))
          (VecNode. (.edit node) arr)))))

  java.lang.Comparable
  (compareTo [this o]
    (if (identical? this o)
      0
      (let [^clojure.lang.IPersistentVector v (cast clojure.lang.IPersistentVector o)
            vcnt (.count v)]
        (cond
          (< cnt vcnt) -1
          (> cnt vcnt) 1
          :else
            (loop [i (int 0)]
              (if (= i cnt)
                0
                (let [comp (clojure.lang.Util/compare (.nth this i) (.nth v i))]
                  (if (= 0 comp)
                    (recur (inc i))
                    comp))))))))

  java.lang.Iterable
  (iterator [this]
    (let [i (java.util.concurrent.atomic.AtomicInteger. 0)]
      (reify java.util.Iterator
        (hasNext [_] (< (.get i) cnt))
        (next [_] (.nth this (dec (.incrementAndGet i))))
        (remove [_] (throw (UnsupportedOperationException.))))))

  java.util.Collection
  (contains [this o] (boolean (some #(= % o) this)))
  (containsAll [this c] (every? #(.contains this %) c))
  (isEmpty [_] (zero? cnt))
  (toArray [this] (into-array Object this))
  (toArray [this arr]
    (if (>= (count arr) cnt)
      (do
        (dotimes [i cnt]
          (aset arr i (.nth this i)))
        arr)
      (into-array Object this)))
  (size [_] cnt)
  (add [_ o] (throw (UnsupportedOperationException.)))
  (addAll [_ c] (throw (UnsupportedOperationException.)))
  (clear [_] (throw (UnsupportedOperationException.)))
  (^boolean remove [_ o] (throw (UnsupportedOperationException.)))
  (removeAll [_ c] (throw (UnsupportedOperationException.)))
  (retainAll [_ c] (throw (UnsupportedOperationException.)))

  java.util.List
  (get [this i] (.nth this i))
  (indexOf [this o]
    (loop [i (int 0)]
      (cond
        (== i cnt) -1
        (= o (.nth this i)) i
        :else (recur (inc i)))))
  (lastIndexOf [this o]
    (loop [i (dec cnt)]
      (cond
        (< i 0) -1
        (= o (.nth this i)) i
        :else (recur (dec i)))))
  (listIterator [this] (.listIterator this 0))
  (listIterator [this i]
    (let [i (java.util.concurrent.atomic.AtomicInteger. i)]
      (reify java.util.ListIterator
        (hasNext [_] (< (.get i) cnt))
        (hasPrevious [_] (pos? i))
        (next [_] (.nth this (dec (.incrementAndGet i))))
        (nextIndex [_] (.get i))
        (previous [_] (.nth this (.decrementAndGet i)))
        (previousIndex [_] (dec (.get i)))
        (add [_ e] (throw (UnsupportedOperationException.)))
        (remove [_] (throw (UnsupportedOperationException.)))
        (set [_ e] (throw (UnsupportedOperationException.))))))
  (subList [this a z] (subvec this a z))
  (add [_ i o] (throw (UnsupportedOperationException.)))
  (addAll [_ i c] (throw (UnsupportedOperationException.)))
  (^Object remove [_ ^int i] (throw (UnsupportedOperationException.)))
  (set [_ i e] (throw (UnsupportedOperationException.)))
)