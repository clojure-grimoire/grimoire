;; A simple implementation using rest for recursing over a collection.  Note that (seq coll) is used as the test.
(defn my-map [func coll]
     (when-let [s (seq coll)]
        (cons (func (first s))
	      (my-map func (rest s)))))