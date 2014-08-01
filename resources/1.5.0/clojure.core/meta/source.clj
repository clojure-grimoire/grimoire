(def
 ^{:arglists '([obj])
   :doc "Returns the metadata of obj, returns nil if there is no metadata."
   :added "1.0"
   :static true}
 meta (fn ^:static meta [x]
        (if (instance? clojure.lang.IMeta x)
          (. ^clojure.lang.IMeta x (meta)))))