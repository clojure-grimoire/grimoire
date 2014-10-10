;; alter is a way to change the value of a reference.

;; Here we're defining a ref named 'names' and setting its value to
;; an empty vector.
user=> (def names (ref []))
#'user/names

;; A function to add a name to the vector (notice the meat's wrapped
;; in a dosync
user=> (defn add-name [name]
         (dosync
           (alter names conj name)))
#'user/add-name

user=> (add-name "zack")
["zack"]

user=> (add-name "shelley")
["zack" "shelley"]

;; Notice that the var 'names' points to the reference that we created
user=> (println names)
#<Ref@658ba380: [zack shelley]>

;; To get the actual value of the ref, you use the '@' symbol, or deref
user=> (println @names)
[zack shelley]

user=> (println (deref names))
[zack shelley]