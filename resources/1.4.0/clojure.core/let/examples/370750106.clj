user=> (let [a (take 5 (range))
             {:keys [b c d] :or {d 10 b 20 c 30}} {:c 50 :d 100}
             [e f g & h] ["a" "b" "c" "d" "e"]
             _ (println "I was here!")
             foo 12
             bar (+ foo 100)]
         [a b c d e f g h foo bar])
I was here!
[(0 1 2 3 4) 20 50 100 "a" "b" "c" ("d" "e") 12 112]
