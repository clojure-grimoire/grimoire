(defn iterator-seq
  "Returns a seq on a java.util.Iterator. Note that most collections
  providing iterators implement Iterable and thus support seq directly."
  {:added "1.0"
   :static true}
  [iter]
  (clojure.lang.IteratorSeq/create iter))