; :as example 

user=> (let [[x y :as my-point] [5 3]]
         (println x y)
         (println my-point))

5 3
[5 3]

; :as names the group you just destructured.

; equivalent to (and better than)

user=> (let [[x y] [5 3]
             my-point [x y]]
         ;...