user=> (filter even? (range 10))
(0 2 4 6 8)
user=> (filter (fn [x]
         (= (count x) 1))
         ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""])
("a" "b" "n" "f" "q")

user=> (filter #(= (count %) 1)
         ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""])
("a" "b" "n" "f" "q")

; When coll is a map, pred is called with key/value
; pairs.
user=> (filter #(> (second %) 100)
	       {:a 1
	        :b 2
	        :c 101
	        :d 102
	        :e -1})
([:c 101] [:d 102])

user=> (into {} *1)
{:c 101, :d 102}
