;; Even though maps, sets, vectors and keywords behave as functions:
user=> ({:a 1} :a)
1

;; fn? still returns false for them because they are not created using fn:
user=> (fn? {:a 1})
false
