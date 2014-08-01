(set [1 2 3 2 1 2 3])
-> #{1 2 3}

#{:a :b :c :d}
-> #{:d :a :b :c}

(hash-set :a :b :c :d)
-> #{:d :a :b :c}
 
(sorted-set :a :b :c :d)
-> #{:a :b :c :d}

;------------------------------------------------

(def s #{:a :b :c :d})
(conj s :e)
-> #{:d :a :b :e :c}
 
(count s)
-> 4
 
(seq s)
-> (:d :a :b :c)
 
(= (conj s :e) #{:a :b :c :d :e})
-> true

(s :b)
-> :b
 
(s :k)
-> nil