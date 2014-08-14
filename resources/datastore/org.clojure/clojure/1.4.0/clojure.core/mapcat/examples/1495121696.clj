;; Suppose you've got a function that takes a value
;; and returns a list of things from it, for example:
(defn f1
  [n]
  [(- n 1) n (+ n 1)])

(f1 1)
;=> [0 1 2]

;; Perhaps you'd like to map it onto each item in a collection:
(map f1 [1 2 3])
;=> ([0 1 2] [1 2 3] [2 3 4])

;; But suppose you wanted them all concatenated? You could do this:
(apply concat (map f1 [1 2 3]))
;=> (0 1 2 1 2 3 2 3 4)

;; Or you could get the same thing with `mapcat`:
(mapcat f1 [1 2 3])
;=> (0 1 2 1 2 3 2 3 4)
