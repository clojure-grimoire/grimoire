;this example illustrates that the dispatch type
;does not have to be a symbol, but can be anything (in this case, it's a string)

(defmulti greeting
  (fn[x] (x "language")))

;params is not used, so we could have used [_]
(defmethod greeting "English" [params]
 "Hello!")

(defmethod greeting "French" [params]
 "Bonjour!")

;then can use this like this:
(def english-map {"id" "1", "language" "English"})
(def  french-map {"id" "2", "language" "French"})

=>(greeting english-map)
"Hello!"
=>(greeting french-map)
"Bounjour!"
