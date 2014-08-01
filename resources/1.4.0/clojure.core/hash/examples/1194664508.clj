user=> (hash "a")
97
user=> (.hashCode "a")  ; notice it is the same hash as java.lang.String.hashCode()
97
user=> (hash [1 2 3])
30817
user=> (hash [1 2 3 4])
955331