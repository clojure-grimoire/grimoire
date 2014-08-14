;; `some-fn` is useful for when you'd use `some` (to find out if any
;; values in a given coll satisfy some predicate), but have more than
;; one predicate. For example, to check if any values in a coll are
;; either even or less than 10:

(or (some even? [1 2 3])
    (some #(< % 10) [1 2 3]))

;; but `some-fn` can save you some duplication here:

((some-fn even? #(< % 10)) 1 2 3)

;; Minor note: the former returns nil if it doesn't find
;; what it's looking for. The latter returns false.