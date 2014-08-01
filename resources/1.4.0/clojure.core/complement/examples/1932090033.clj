;; a simple not-empty? predicate
user=> (def not-empty? (complement empty?))
#'user/not-empty?
user=> (not-empty? [])
false
user=> (not-empty? [1 2])
true


;; a slightly more complex example
;; this function takes two arguments, and sometimes returns nil

user=> (defn contains-char? [the-string, the-char]
         (some #(= the-char %) the-string))
#'user/contains-char?

user=> (contains-char? "abc" \b)
true
user=> (contains-char? "abc" \j)
nil

;; define the complement, to check if a char is absent
user=> (def does-not-contain-char? (complement contains-char?))
#'user/does-not-contain-char?

;; our complement does exactly what we expect
user=> (does-not-contain-char? "abc" \b)
false
user=> (does-not-contain-char? "abc" \j)
true
