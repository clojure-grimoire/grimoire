It is a common mistake to think of the English meaning of the word
'contains', and believe that therefore contains? will tell you whether
a vector or array contains a value.  See 'some' if that is what you
want.

contains? is good for checking whether a map has a mapping for a key,
or a set contains an element.  It can be easier to use correctly than
'get', especially if you wish to allow a key, value, or set element to
be nil.

Examples:

    user=> (contains? #{:a :b 5 nil} :b)       ; :b is in the set
    true
    user=> (contains? #{:a :b 5 nil} 2)        ; 2 is not
    false
    user=> (contains? #{:a :b 5 nil} nil)      ; nil is in this set
    true
    user=> (contains? #{:a :b 5} nil)          ; but not in this one
    false

    user=> (contains? {:a "a" nil "nil"} :a)   ; key :a is in the map
    true
    user=> (contains? {:a "a" nil "nil"} :b)   ; :b is not
    false
    user=> (contains? {:a "a" nil "nil"} nil)  ; nil is a key here
    true
    user=> (contains? {:a "a"} nil)            ; but not here
    false

contains? also works for Java collections implementing interfaces
java.util.Set or java.util.Map.

It is not as useful, but contains? can also determine whether a number
lies within the range of defined indices of a vector, string, or Java
array.  For strings and Java arrays, it is identical in these cases to
(and (0 <= i) (< i (count coll))) where i is equal to (. key
intValue).  The behavior is the same for vectors, except only integer
values can return true.

    user=> (contains? "abcdef" 5)
    true       ; max string index is 5
    user=> (contains? [:a :b :c] 1)
    true       ; max vector index is 2
    user=> (contains? (int-array [28 35 42 49]) 10)
    false      ; max array index is 3

The intValue conversion can lead to unexpected behavior, demonstrated
below.  This happens because intValue converts the long and double
values shown to 0, which is in the range [0,2] of indices.

    user=> (def long-truncates-to-int-0 (bit-shift-left 1 33))
    user=> (contains? "abc" long-truncates-to-int-0)
    true
    user=> (contains? "abc" -0.99)
    true
    user=> (contains? [:a :b :c] long-truncates-to-int-0)
    true
    user=> (contains? [:a :b :c] 0.5)
    false       ; only integer values can return true for vectors

See also: some, get
