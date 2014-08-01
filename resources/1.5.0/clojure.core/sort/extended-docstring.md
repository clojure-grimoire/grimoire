If you supply a comparator, it must implement the Java Comparator
interface, but this includes Clojure functions that implement a 3-way
or boolean comparator.  See (topic Comparators) for details on boolean
comparators.

sort is guaranteed to be stable, since it is implemented using the
sort method of Java's java.util.Arrays class.  This means that if two
values in the input collection are considered equal by the comparator,
they are guaranteed to remain in the same relative order in the output
as they had in the input.

Examples:

    user=> (sort [3 -7 10 8 5.3 9/5 -7.1])
    (-7.1 -7 9/5 3 5.3 8 10)
    user=> (sort #(compare %2 %1) '(apple banana aardvark zebra camel))
    (zebra camel banana apple aardvark)

    user=> (def x (to-array [32 9 11]))
    #'user/x
    user=> (seq x)
    (32 9 11)
    user=> (sort x)   ; returns sorted sequence
    (9 11 32)
    user=> (seq x)    ; but also modifies Java array x
    (9 11 32)
    user=> (sort (aclone x))   ; can avoid this by copying the array
    (9 11 32)
    ;; Such copying is unnecessary for args that are not a Java array

See also: sort-by, compare, (topic Comparators)
