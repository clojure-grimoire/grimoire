;;; http://en.wikipedia.org/wiki/Fizz_buzz
(def fizzbuzz
  (some-fn #(and (= (mod % 3) 0) (= (mod % 5) 0) "FizzBuzz")
           #(and (= (mod % 3) 0) "Fizz")
           #(and (= (mod % 5) 0) "Buzz")))

(doseq [n (take 17 (rest (range)))]
  (println (or (fizzbuzz n) n)))

1
2
Fizz
4
Buzz
Fizz
7
8
Fizz
Buzz
11
Fizz
13
14
FizzBuzz
16
17