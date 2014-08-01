(use 'clojure.walk)

(postwalk-replace {:a 1 :b 2} [:a :b])
;=> [1 2]

(postwalk-replace {:a 1 :b 2} [:a :b :c])
;=> [1 2 :c]

(postwalk-replace {:a 1 :b 2} [:a :b [:a :b] :c])
;=> [1 2 [1 2] :c]