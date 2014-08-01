user=> (hash-set 1 2 1 3 1 4 1 5)
#{1 2 3 4 5}

user=> (= (hash-set :c :a :b) #{:b :a :c})
true 


user=> (hash-set (seq "Lorem ipsum dolor sit amet"))
#{(\L \o \r \e \m \space \i \p \s \u \m \space \d \o \l \o \r \space \s \i \t \space \a \m \e \t)} 

user=> (apply hash-set (seq "Lorem ipsum dolor sit amet"))
#{\space \a \d \e \i \L \l \m \o \p \r \s \t \u} 


;; Caution: hash-set now prevents duplicate keys (Clojure 1.2)
user=> (hash-set 1 1)
Duplicate key: 1
  [Thrown class java.lang.IllegalArgumentException]
