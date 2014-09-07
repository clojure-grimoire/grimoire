(defn remove-watch
  "Alpha - subject to change.
  Removes a watch (set by add-watch) from a reference"
  {:added "1.0"
   :static true}
  [^clojure.lang.IRef reference key]
  (.removeWatch reference key))