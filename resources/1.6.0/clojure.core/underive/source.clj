(defn underive
  "Removes a parent/child relationship between parent and
  tag. h must be a hierarchy obtained from make-hierarchy, if not
  supplied defaults to, and modifies, the global hierarchy."
  {:added "1.0"}
  ([tag parent] (alter-var-root #'global-hierarchy underive tag parent) nil)
  ([h tag parent]
    (let [parentMap (:parents h)
	  childsParents (if (parentMap tag)
			  (disj (parentMap tag) parent) #{})
	  newParents (if (not-empty childsParents)
		       (assoc parentMap tag childsParents)
		       (dissoc parentMap tag))
	  deriv-seq (flatten (map #(cons (key %) (interpose (key %) (val %)))
				       (seq newParents)))]
      (if (contains? (parentMap tag) parent)
	(reduce1 #(apply derive %1 %2) (make-hierarchy)
		(partition 2 deriv-seq))
	h))))