;; replaces all a's with 1 and all b's with 2
user=>(clojure.string/replace "a b a" #"a|b" {"a" "1" "b" "2"})
"1 2 1"