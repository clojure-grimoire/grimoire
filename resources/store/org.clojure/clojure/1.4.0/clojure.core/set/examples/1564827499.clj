user=> (set [1 2 3 2 1 2 3])
#{1 2 3}

user=> #{:a :b :c :d}
#{:d :a :b :c}

user=> (hash-set :a :b :c :d)
#{:d :a :b :c}
 
user=> (sorted-set :a :b :c :d)
#{:a :b :c :d}
