(defn -reset-methods [protocol]
  (doseq [[^clojure.lang.Var v build] (:method-builders protocol)]
    (let [cache (clojure.lang.MethodImplCache. protocol (keyword (.sym v)))]
      (.bindRoot v (build cache)))))