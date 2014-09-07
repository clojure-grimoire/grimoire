If you supply a comparator, it must implement the Java Comparator
interface, but this includes Clojure functions that implement a 3-way
or boolean comparator.  See (topic Comparators) for details on boolean
comparators.

sort is guaranteed to be stable, since it is implemented using the
sort method of Java's java.util.Arrays class.  This means that if two
values in the input collection are considered equal by the comparator,
they are guaranteed to remain in the same relative order in the output
as they had in the input.
