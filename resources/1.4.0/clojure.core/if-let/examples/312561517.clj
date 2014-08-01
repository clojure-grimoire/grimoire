user=> (if-let [x false y true]
         "then"
         "else")
java.lang.IllegalArgumentException: if-let requires exactly 2 forms in binding vector (NO_SOURCE_FILE:1)

user=> (defn if-let-demo [arg]
         (if-let [x arg]
           "then"
           "else"))

user=> (if-let-demo 1) ; anything except nil/false
"then"
user=> (if-let-demo nil)
"else"
user=> (if-let-demo false)
"else"
