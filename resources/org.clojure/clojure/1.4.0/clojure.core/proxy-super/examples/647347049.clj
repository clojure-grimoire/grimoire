;; Create a proxy for java.util.ArrayList that overrides the add() 
;; method and calls the super class implementation using proxy-super.
(def lst (proxy [java.util.ArrayList] []
           (add [x]
             (println "Adding some stuff:" x)
             (proxy-super add x))))

user=> (.add lst 1)
Adding some stuff: 1
true

user=> (.add lst 2)
Adding some stuff: 2
true

user=> (.add lst [:this :is :some :other :stuff])
Adding some stuff: [:this :is :some :other :stuff]
true

user=> (.size lst)
3


 