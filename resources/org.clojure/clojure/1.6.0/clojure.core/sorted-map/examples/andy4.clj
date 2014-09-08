;; Searching sorted maps calls the comparator with the searched-for value
;; and some of the keys in the map, which throws an exception if the
;; comparator does.

user=> (m2 1)
"bigdecone"
user=> (m2 "a")   ; this gives exception from compare
ClassCastException java.lang.Double cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)
