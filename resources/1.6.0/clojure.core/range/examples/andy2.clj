;; Just as when increasing, when decreasing the final value is not
;; included in the result.
user=> (range 11 0 -1)
(11 10 9 8 7 6 5 4 3 2 1)
user=> (range 11 -1 -1)
(11 10 9 8 7 6 5 4 3 2 1 0)
