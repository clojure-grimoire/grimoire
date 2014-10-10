(defn shuffle
  "Return a random permutation of coll"
  {:added "1.2"
   :static true}
  [^java.util.Collection coll]
  (let [al (java.util.ArrayList. coll)]
    (java.util.Collections/shuffle al)
    (clojure.lang.RT/vector (.toArray al))))