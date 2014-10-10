user=> (def fractions 
         (for [n (range 1 100) d (range (inc n) 100)] 
           (let [gcd (clojure.contrib.math/gcd n d)] 
             (/ (/ n gcd) (/ d gcd)))))
;; all irreducible fractions with denominator < 100
;; (1/2 1/3 ... 1/99 2/3 1/2 2/5 1/3 ...)

user=> (count fractions)
4851

user=> (count (distinct fractions))
3003
