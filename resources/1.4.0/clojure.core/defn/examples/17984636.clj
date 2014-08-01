user=> (defn foo [a b c]
	    (* a b c))
#'user/foo
user=> (foo 1 2 3)
6

user=> (defn bar [a b & [c]]
         (if c
           (* a b c)
           (* a b 100)))
#'user/bar
user=> (bar 5 6)
3000
user=> (bar 5 6 2)
60

user=> (defn baz [a b & {:keys [c d] :or {c 10 d 20}}]
         (* a b c d))
#'user/baz
user=> (baz 2 3)
1200
user=> (baz 2 3 :c 5)
600
user=> (baz 2 3 :c 5 :d 6)
180

user=> (defn boo [a b & {:keys [c d] :or {c 10 d 20} :as all-specified}]
          (println all-specified)
          (* a b c d))
#'user/boo
user=> (boo 2 3)
nil
1200
user=> (boo 2 3 :c 5)
{:c 5}
600
user=> (boo 1 2 :d 3 :c 4)
{:c 4, :d 3}
24
