;; This example takes a list of keys and a separate list of values and 
;; inserts them into a map.
user=> (apply assoc {} 
         (interleave [:fruit :color :temp] 
                     ["grape" "red" "hot"]))

{:temp "hot", :color "red", :fruit "grape"}
