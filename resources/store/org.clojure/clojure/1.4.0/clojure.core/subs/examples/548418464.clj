user=> (subs "Clojure" 1)    
"lojure"
user=> (subs "Clojure" 1 3)
"lo"


;; String indexes have to be between 0 and (.length s)

user=> (subs "Clojure" 1 20)
java.lang.StringIndexOutOfBoundsException: String index out of range: 20 (NO_SOURCE_FILE:0)
