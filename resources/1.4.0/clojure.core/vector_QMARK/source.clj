(def
 ^{:arglists '([x])
   :doc "Return true if x implements IPersistentVector"
   :added "1.0"
   :static true}
 vector? (fn ^:static vector? [x] (instance? clojure.lang.IPersistentVector x)))