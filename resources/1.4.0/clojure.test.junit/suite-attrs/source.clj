(defn suite-attrs
  [package classname]
  (let [attrs {:name classname}]
    (if package
      (assoc attrs :package package)
      attrs)))