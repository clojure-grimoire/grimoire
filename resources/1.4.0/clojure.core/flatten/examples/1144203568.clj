user=> (flatten [1 [2 3]])
(1 2 3)

user=> (flatten '(1 2 3))
(1 2 3)

user=> (flatten '(1 2 [3 (4 5)])) 
(1 2 3 4 5)

user=> (flatten nil)
()

; Attention with stuff which is not a sequence

user=> (flatten 5)
()

user=> (flatten {:name "Hubert" :age 23})
()

; Workaround for maps

user=> (flatten (seq {:name "Hubert" :age 23}))
(:name "Hubert" :age 23)