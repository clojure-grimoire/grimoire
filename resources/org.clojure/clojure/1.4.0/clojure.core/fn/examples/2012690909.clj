;; simple anonymous function passed to (map )
user=> (map (fn [x] (* x x)) (range 1 10))
(1 4 9 16 25 36 49 64 81) 

;; anonymous function with a name.  not so anonymous now is it?
;; this is useful in stack traces
(fn add[a b] (+ a b))

;; anonymous function with two params, the second is destructed
user=> (reduce (fn [m [k v]] (assoc m v k)) {} {:b 2 :a 1 :c 3})
{2 :b, 1 :a, 3 :c} 

;; define and instantly call an anonymous function
user=> ((fn [a b c] (+ a b c)) 2 4 6)
12

;; define and instantly call an anonymous variadic function 
;; "nums" is a list here
user=> ((fn [& nums] (/ (apply + nums) (count nums))) 1 2 3 4)
5/2 

;; define and instantly call an anonymous mixed function
;; "nums" is a list, while "int" is a number
user=> ((fn [int & nums] (+ int (/ (apply + nums) (count nums)))) 10 1 2 3 4)
25/2 

;; define and instantly call an anonymous overloaded function 
;; even though it is quite pointless
user=>  ((fn ([a] (inc a)) ([a b] (+ a b))) 3)
4

