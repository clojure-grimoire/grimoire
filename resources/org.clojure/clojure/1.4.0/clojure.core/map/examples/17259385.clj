user=> (map inc [1 2 3 4 5])
(2 3 4 5 6)


;; map can be used with multiple collections. Collections will be consumed
;; and passed to the mapping function in parallel:
user=> (map + [1 2 3] [4 5 6])
(5 7 9)


;; When map is passed more than one collection, the mapping function will
;; be applied until one of the collections runs out:
user=> (map + [1 2 3] (iterate inc 1))
(2 4 6)



;; map is often used in conjunction with the # reader macro:
user=> (map #(str "Hello " % "!" ) ["Ford" "Arthur" "Tricia"])
("Hello Ford!" "Hello Arthur!" "Hello Tricia!")

;; A useful idiom to pull "columns" out of a collection of collections. 
;; Note, it is equivalent to:
;; user=> (map vector [:a :b :c] [:d :e :f] [:g :h :i])

user=> (apply map vector [[:a :b :c]
                          [:d :e :f]
                          [:g :h :i]])

([:a :d :g] [:b :e :h] [:c :f :i])

;; From http://clojure-examples.appspot.com/clojure.core/map