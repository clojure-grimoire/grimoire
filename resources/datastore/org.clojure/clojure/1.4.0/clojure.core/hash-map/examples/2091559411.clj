user=> (map #(hash-map % 0) (seq "abcdefgh"))
({\a 0} {\b 0} {\c 0} {\d 0} {\e 0} {\f 0} {\g 0} {\h 0}) 

user=> (apply hash-map (.split "a 1 b 2 c 3" " "))
{"a" "1", "b" "2", "c" "3"}