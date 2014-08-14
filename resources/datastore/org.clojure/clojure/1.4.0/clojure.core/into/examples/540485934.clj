; Maps can be constructed from a sequence of 2-vectors or a sequence 
; of maps
user=> (into (sorted-map) [ [:a 1] [:c 3] [:b 2] ] )
{:a 1, :b 2, :c 3}
user=> (into (sorted-map) [ {:a 1} {:c 3} {:b 2} ] )
{:a 1, :b 2, :c 3}

; When maps are the input source, they convert into an unordered sequence 
; of key-value pairs, encoded as 2-vectors
user=> (into [] {1 2, 3 4})
[[1 2] [3 4]]
