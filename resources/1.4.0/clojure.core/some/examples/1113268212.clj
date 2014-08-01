;; some can be used as a substitute for (first (filter ...
;; in most cases

user=> (first (filter even? [1 2 3 4]))
2
user=> (some #(if (even? %) %) [1 2 3 4])
2
user=>