(defn remove-ns
  "Removes the namespace named by the symbol. Use with caution.
  Cannot be used to remove the clojure namespace."
  {:added "1.0"
   :static true}
  [sym] (clojure.lang.Namespace/remove sym))