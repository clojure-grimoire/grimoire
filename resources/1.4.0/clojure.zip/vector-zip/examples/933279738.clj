
(require '[clojure.zip :as zip])

(defn zip-map [f loc]
  " Map f over every node of the zipper.
    The function received has the form (f node-value loc),
    the node value and its location"
  (loop [z loc]
    (if (zip/end? z)
      (zip/root z) ; perhaps you can call zip/seq-zip or zip/vector-zip?
      (recur (zip/next (zip/edit z f z))))))

;; Multiply by 100 every node in the tree
user=> (zip-map (fn [n nx] (if (vector? n) n (* n 100) )) (zip/vector-zip '[5 [10 20 30] [1 2 3] ]))
;; Be careful! the returned result by zip/root is not a zipper anymore!
[500 [1000 2000 3000] [100 200 300]]
