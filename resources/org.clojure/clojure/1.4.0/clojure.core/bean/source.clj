(defn bean
  "Takes a Java object and returns a read-only implementation of the
  map abstraction based upon its JavaBean properties."
  {:added "1.0"}
  [^Object x]
  (let [c (. x (getClass))
	pmap (reduce1 (fn [m ^java.beans.PropertyDescriptor pd]
			 (let [name (. pd (getName))
			       method (. pd (getReadMethod))]
			   (if (and method (zero? (alength (. method (getParameterTypes)))))
			     (assoc m (keyword name) (fn [] (clojure.lang.Reflector/prepRet (.getPropertyType pd) (. method (invoke x nil)))))
			     m)))
		     {}
		     (seq (.. java.beans.Introspector
			      (getBeanInfo c)
			      (getPropertyDescriptors))))
	v (fn [k] ((pmap k)))
        snapshot (fn []
                   (reduce1 (fn [m e]
                             (assoc m (key e) ((val e))))
                           {} (seq pmap)))]
    (proxy [clojure.lang.APersistentMap]
           []
      (containsKey [k] (contains? pmap k))
      (entryAt [k] (when (contains? pmap k) (new clojure.lang.MapEntry k (v k))))
      (valAt ([k] (v k))
	     ([k default] (if (contains? pmap k) (v k) default)))
      (cons [m] (conj (snapshot) m))
      (count [] (count pmap))
      (assoc [k v] (assoc (snapshot) k v))
      (without [k] (dissoc (snapshot) k))
      (seq [] ((fn thisfn [plseq]
		  (lazy-seq
                   (when-let [pseq (seq plseq)]
                     (cons (new clojure.lang.MapEntry (first pseq) (v (first pseq)))
                           (thisfn (rest pseq)))))) (keys pmap))))))