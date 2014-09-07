;; It is not as useful, but contains? can also determine whether a number
;; lies within the range of defined indices of a vector, string, or Java
;; array.  For strings and Java arrays, it is identical in these cases to
;; (and (0 <= i) (< i (count coll))) where i is equal to (. key intValue).
;; The behavior is the same for vectors, except only integer values
;; can return true.

user=> (contains? "abcdef" 5)
true       ; max string index is 5
user=> (contains? [:a :b :c] 1)
true       ; max vector index is 2
user=> (contains? (int-array [28 35 42 49]) 10)
false      ; max array index is 3
