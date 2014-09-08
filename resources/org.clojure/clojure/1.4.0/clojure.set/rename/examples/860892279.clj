;; Here's a relation with two keys (think "column names" in SQL), :a and :b

user=> (def relation #{  {:a 1, :b 1}  {:a 2, :b 2} })


;; I decide that :a is a stupid name and that :new-a would be better. 
;; Here's how I make a new relation with the renamed key:

user=> (rename relation {:a :new-a})
#{{:new-a 1, :b 1} {:new-a 2, :b 2}}

