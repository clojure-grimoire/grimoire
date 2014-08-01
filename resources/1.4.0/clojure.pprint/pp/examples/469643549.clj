user=> (zipmap 
        [:a :b :c :d :e] 
        (repeat 
          (zipmap 
            [:a :b :c :d :e] 
            (take 5 (range)))))
{:e {:e 4, :d 3, :c 2, :b 1, :a 0}, :d {:e 4, :d 3, :c 2, :b 1, :a 0}, :c {:e 4, :d 3, :c 2, :b 1, :a 0}, :b {:e 4, :d 3, :c 2, :b 1, :a 0}, :a {:e 4, :d 3, :c 2, :b 1, :a 0}}

user=> (clojure.pprint/pp)
{:e {:e 4, :d 3, :c 2, :b 1, :a 0},
 :d {:e 4, :d 3, :c 2, :b 1, :a 0},
 :c {:e 4, :d 3, :c 2, :b 1, :a 0},
 :b {:e 4, :d 3, :c 2, :b 1, :a 0},
 :a {:e 4, :d 3, :c 2, :b 1, :a 0}}
nil
