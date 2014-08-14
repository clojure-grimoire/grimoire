user=> (def users [{:name "James" :age 26}  {:name "John" :age 43}])
 
user=> (assoc-in users [1 :age] 44)
[{:name "James", :age 26} {:name "John", :age 44}]

user=> (assoc-in users [1 :password] "nhoJ")
[{:name "James", :age 26} {:password "nhoJ", :name "John", :age 43}]

;; Also (assoc m 2 {...}) or (conj m {...})
user=> (assoc-in users [2] {:name "Jack" :age 19})  
[{:name "James", :age 26} {:name "John", :age 43} {:name "Jack", :age 19}]




;; From http://clojure-examples.appspot.com/clojure.core/assoc-in