user=> (def negative-quotient (comp - /))
#'user/negative-quotient

user=> (negative-quotient 8 3)           
-8/3


user=> (def concat-and-reverse (comp (partial apply str) reverse str)) 
#'user/concat-and-reverse

user=> (concat-and-reverse "hello" "clojuredocs")
"scoderujolcolleh"
