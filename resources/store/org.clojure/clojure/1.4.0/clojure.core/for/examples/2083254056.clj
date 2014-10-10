user=> (def digits (seq [1 2 3]))
user=> (for [x1 digits x2 digits] (* x1 x2))
(1 2 3 2 4 6 3 6 9)