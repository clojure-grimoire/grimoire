user=> (coll? {})
true

user=> (coll? #{})
true

user=> (coll? [])
true

user=> (coll? ())
true

user=> (coll? 4)
false

user=> (coll? "fred")
false

user=> (coll? true)
false

user=> (coll? nil)
false
