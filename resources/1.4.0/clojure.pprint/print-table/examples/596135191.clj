user=> (use 'clojure.pprint)
nil

;; By default, columns are in the order returned by (keys (first rows))
user=> (print-table [{:a 1 :b 2 :c 3} {:b 5 :a 7 :c "dog"}])
=============
:a | :c  | :b
=============
1  | 3   | 2 
7  | dog | 5 
=============
nil

;; If there are keys not in the first row, and/or you want to specify only
;; some, or in a particular order, give the desired keys as the first arg.
user=> (print-table [:b :a] [{:a 1 :b 2 :c 3} {:b 5 :a 7 :c "dog"}])
=======
:b | :a
=======
2  | 1 
5  | 7 
=======
nil
