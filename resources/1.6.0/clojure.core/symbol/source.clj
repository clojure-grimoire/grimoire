(defn symbol
  "Returns a Symbol with the given namespace and name."
  {:tag clojure.lang.Symbol
   :added "1.0"
   :static true}
  ([name] (if (symbol? name) name (clojure.lang.Symbol/intern name)))
  ([ns name] (clojure.lang.Symbol/intern ns name)))