;; returns distinct elements
user=> (set '(1 1 2 3 2 4 5 5))
#{1 2 3 4 5}

;; returns distinct elements (different nomenclature)
user=> (set [1 1 2 3 2 4 5 5])
#{1 2 3 4 5}

user=> (set [1 2 3 4 5])  
#{1 2 3 4 5}

user=> (set "abcd")
#{\a \b \c \d}

user=> (set '("a" "b" "c" "d"))
#{"a" "b" "c" "d"}

user=> (set {:one 1 :two 2 :three 3})
#{[:two 2] [:three 3] [:one 1]}
