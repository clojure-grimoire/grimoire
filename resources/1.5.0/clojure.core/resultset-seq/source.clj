(defn resultset-seq
  "Creates and returns a lazy sequence of structmaps corresponding to
  the rows in the java.sql.ResultSet rs"
  {:added "1.0"}
  [^java.sql.ResultSet rs]
    (let [rsmeta (. rs (getMetaData))
          idxs (range 1 (inc (. rsmeta (getColumnCount))))
          keys (map (comp keyword #(.toLowerCase ^String %))
                    (map (fn [i] (. rsmeta (getColumnLabel i))) idxs))
          check-keys
                (or (apply distinct? keys)
                    (throw (Exception. "ResultSet must have unique column labels")))
          row-struct (apply create-struct keys)
          row-values (fn [] (map (fn [^Integer i] (. rs (getObject i))) idxs))
          rows (fn thisfn []
                 (when (. rs (next))
                   (cons (apply struct row-struct (row-values)) (lazy-seq (thisfn)))))]
      (rows)))