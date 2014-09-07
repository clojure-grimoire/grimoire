;; We can use get-in for reaching into nested maps:
user=> (def m {:username "sally"
               :profile {:name "Sally Clojurian"
                         :address {:city "Austin" :state "TX"}}})
#'user/m

user=> (get-in m [:profile :name])
"Sally Clojurian"
user=> (get-in m [:profile :address :city])
"Austin"
user=> (get-in m [:profile :address :zip-code])
nil
user=> (get-in m [:profile :address :zip-code] "no zip code!")
"no zip code!"


;; Vectors are also associative:
user=> (def v [[1 2 3]
               [4 5 6]
               [7 8 9]])
#'user/v
user=> (get-in v [0 2])
3
user=> (get-in v [2 1])
8


;; We can mix associative types:
user=> (def mv {:username "jimmy"
                :pets [{:name "Rex"
                        :type :dog}
                       {:name "Sniffles"
                        :type :hamster}]})
#'user/mv
user=> (get-in mv [:pets 1 :type])
:hamster
