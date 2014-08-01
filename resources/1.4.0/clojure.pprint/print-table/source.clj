(defn print-table
  "Alpha - subject to change.
   Prints a collection of maps in a textual table. Prints table headings
   ks, and then a line of output for each row, corresponding to the keys
   in ks. If ks are not specified, use the keys of the first item in rows."
  {:added "1.3"}
  ([ks rows]
     (when (seq rows)
       (let [widths (map
                     (fn [k]
                       (apply max (count (str k)) (map #(count (str (get % k))) rows)))
                     ks)
             fmts (map #(str "%-" % "s") widths)
             fmt-row (fn [row]
                       (apply str (interpose " | "
                                             (for [[col fmt] (map vector (map #(get row %) ks) fmts)]
                                               (format fmt (str col))))))
             header (fmt-row (zipmap ks ks))
             bar (apply str (repeat (count header) "="))]
         (println bar)
         (println header)
         (println bar)
         (doseq [row rows]
           (println (fmt-row row)))
         (println bar))))
  ([rows] (print-table (keys (first rows)) rows)))