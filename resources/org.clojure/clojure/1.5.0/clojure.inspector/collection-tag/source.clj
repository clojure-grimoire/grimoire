(defn collection-tag [x]
  (cond 
   (instance? java.util.Map$Entry x) :entry
   (instance? java.util.Map x) :map
   (sequential? x) :seq
   :else :atom))