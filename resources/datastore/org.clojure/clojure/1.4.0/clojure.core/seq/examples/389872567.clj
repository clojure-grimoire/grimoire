; seq can be used to turn a map into a list of vectors
; notice how the list is built adding elements to the start of the list
; not to the end, like in vectors
user=> (seq {:key1 "value1" :key2 "value2"})
([:key2 "value 2"] [:key1 "value 1"])