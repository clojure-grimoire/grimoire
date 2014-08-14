;; expressions are calculated in parallel

user=> (pvalues (expensive-calc-1) (expensive-calc-2))
(2330 122)
