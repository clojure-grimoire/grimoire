(defn drop-last
  "Return a lazy sequence of all but the last n (default 1) items in coll"
  {:added "1.0"
   :static true}
  ([s] (drop-last 1 s))
  ([n s] (map (fn [x _] x) s (drop n s))))