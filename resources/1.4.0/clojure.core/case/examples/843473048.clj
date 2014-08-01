user=> (let [mystr "hello"]
         (case mystr
           "" 0
           "hello" (count mystr)))
5

user=> (let [mystr "no match"]
         (case mystr
               "" 0
               "hello" (count mystr)))
No matching clause: no match
  [Thrown class java.lang.IllegalArgumentException]

user=> (let [mystr "no match"]
         (case mystr
               "" 0
               "hello" (count mystr)
               "default"))
"default"

;; You can give multiple values for the same condition by putting
;; those values in a list.
user=> (case 'y
             (x y z) "x, y, or z"
             "default")
"x, y, or z"

user=> (let [myseq '(1 2)]
         (case myseq
               (()) "empty seq"
               ((1 2)) "my seq"
               "default"))
"my seq"

;; "The test-constants are not evaluated.They must be compile-time
;; literals, and need not be quoted." 
user=> (let [myvec [1 2]]
         (case myvec
               [] "empty vec"
               (vec '(1 2)) "my vec"
               "default"))
"default"
