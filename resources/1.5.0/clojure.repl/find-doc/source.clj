(defn find-doc
  "Prints documentation for any var whose documentation or name
 contains a match for re-string-or-pattern"
  {:added "1.0"}
  [re-string-or-pattern]
    (let [re (re-pattern re-string-or-pattern)
          ms (concat (mapcat #(sort-by :name (map meta (vals (ns-interns %))))
                             (all-ns))
                     (map namespace-doc (all-ns))
                     (map special-doc (keys special-doc-map)))]
      (doseq [m ms
              :when (and (:doc m)
                         (or (re-find (re-matcher re (:doc m)))
                             (re-find (re-matcher re (str (:name m))))))]
               (print-doc m))))