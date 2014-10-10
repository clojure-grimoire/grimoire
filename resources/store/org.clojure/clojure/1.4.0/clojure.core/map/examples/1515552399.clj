; map passed two collection arguments. From 4Clojure Problem #157

(def d1 [:a :b :c])
(#(map list % (range)) d1)
((:a 0) (:b 1) (:c 2))