(defn seq-zip
  "Returns a zipper for nested sequences, given a root sequence"
  {:added "1.0"}
  [root]
    (zipper seq?
            identity
            (fn [node children] (with-meta children (meta node)))
            root))