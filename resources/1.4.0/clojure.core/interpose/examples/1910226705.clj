;; The quintessential interpose example:
user> (def my-strings ["one" "two" "three"])

user> (interpose ", " my-strings)
=> ("one" ", " "two" ", " "three")

user> (apply str (interpose ", " my-strings))
=> "one, two, three"

;; Might use clojure.string/join if the plan is to join
(use '[clojure.string :only (join)])
user> (join ", " my-strings)
=> "one, two, three"