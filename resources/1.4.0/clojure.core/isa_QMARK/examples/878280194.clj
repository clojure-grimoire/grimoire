user=> (import 'java.util.PriorityQueue)
java.util.PriorityQueue

user=> (bases PriorityQueue)
(java.util.AbstractQueue java.io.Serializable)

user=> (import 'java.util.AbstractQueue)
java.util.AbstractQueue

user=> (isa? PriorityQueue AbstractQueue)
true

user=> (bases AbstractQueue)
(java.util.AbstractCollection java.util.Queue)

user=> (isa? PriorityQueue java.util.AbstractCollection)
true

user=> (isa? PriorityQueue java.util.Queue)
true

user=> (isa? java.util.PriorityQueue java.util.TreeMap)
false