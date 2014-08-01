user=> (map
         #(when-not (= %2 %3) [%1 %2 %3])
         (iterate inc 0)
         [:a :b :c]
         [:a :a :a])

(nil [1 :b :a] [2 :c :a])
