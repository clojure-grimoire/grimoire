(defn reset-meta!
  "Atomically resets the metadata for a namespace/var/ref/agent/atom"
  {:added "1.0"
   :static true}
 [^clojure.lang.IReference iref metadata-map] (.resetMeta iref metadata-map))