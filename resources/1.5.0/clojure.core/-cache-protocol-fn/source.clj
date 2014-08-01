(defn -cache-protocol-fn [^clojure.lang.AFunction pf x ^Class c ^clojure.lang.IFn interf]
  (let [cache  (.__methodImplCache pf)
        f (if (.isInstance c x)
            interf 
            (find-protocol-method (.protocol cache) (.methodk cache) x))]
    (when-not f
      (throw (IllegalArgumentException. (str "No implementation of method: " (.methodk cache) 
                                             " of protocol: " (:var (.protocol cache)) 
                                             " found for class: " (if (nil? x) "nil" (.getName (class x)))))))
    (set! (.__methodImplCache pf) (expand-method-impl-cache cache (class x) f))
    f))