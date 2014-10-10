;; Use a hash-map as a function to translate values in a collection from the 
;; given key to the associated value

user=> (map {2 "two" 3 "three"} [5 3 2])
(nil "three" "two")

;; then use (filter identity... to remove the nils
user=> (filter identity (map {2 "two" 3 "three"} [5 3 2]))
("three" "two")