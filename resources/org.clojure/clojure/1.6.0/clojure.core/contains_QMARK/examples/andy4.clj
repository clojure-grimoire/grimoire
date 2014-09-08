;; The intValue conversion can lead to unexpected behavior, demonstrated
;; below.  This happens because intValue converts the long and double
;; values shown to 0, which is in the range [0,2] of indices.

user=> (def long-truncates-to-int-0 (bit-shift-left 1 33))
user=> (contains? "abc" long-truncates-to-int-0)
true
user=> (contains? "abc" -0.99)
true
user=> (contains? [:a :b :c] long-truncates-to-int-0)
true
user=> (contains? [:a :b :c] 0.5)
false       ; only integer values can return true for vectors
