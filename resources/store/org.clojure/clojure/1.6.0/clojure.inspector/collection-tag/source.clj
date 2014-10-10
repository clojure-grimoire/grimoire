(defn collection-tag [x]
  (cond 
   (instance? java.util.Map$Entry x) :entry
   (instance? java.util.Map x) :seqable
   (instance? java.util.Set x) :seqable
   (sequential? x) :seq
   (instance? clojure.lang.Seqable x) :seqable
   :else :atom))