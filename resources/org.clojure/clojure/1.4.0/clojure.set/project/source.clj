(defn project
  "Returns a rel of the elements of xrel with only the keys in ks"
  {:added "1.0"}
  [xrel ks]
    (set (map #(select-keys % ks) xrel)))