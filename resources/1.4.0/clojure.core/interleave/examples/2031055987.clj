;; The shortest input stops interleave:

user=> (interleave [:a :b] (iterate inc 1))
(:a 1 :b 2)