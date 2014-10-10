;; A really goofy way to find the size of a collection
user=> (reduce + (map (constantly 1) [:a :b :c]))
3