(defn aclone
  "Returns a clone of the Java array. Works on arrays of known
  types."
  {:inline (fn [a] `(. clojure.lang.RT (aclone ~a)))
   :added "1.0"}
  [array] (. clojure.lang.RT (aclone array)))