user=> (Integer/toBinaryString 235)
"11101011"
user=> (Integer/toBinaryString 199)
"11000111"
user=> (bit-and 235 199)
195
user=> (Integer/toBinaryString 195)
"11000011"

;;11101011
;;&
;;11000111
;;=
;;11000011