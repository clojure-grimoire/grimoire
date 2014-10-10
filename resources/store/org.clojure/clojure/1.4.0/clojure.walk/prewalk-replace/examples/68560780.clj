(use 'clojure.walk)

(prewalk-replace {:a 1 :b 2} [:a :b])
;=> [1 2]

(prewalk-replace {:a 1 :b 2} [:a :b :c])
;=> [1 2 :c]

(prewalk-replace {:a 1 :b 2} [:a :b [:a :b] :c])
;=> [1 2 [1 2] :c]