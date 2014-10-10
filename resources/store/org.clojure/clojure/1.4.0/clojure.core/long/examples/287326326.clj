v.1.3.0
user=> (let [num (* 1234567890 21)] [num (int num) (long num)])
[25925925690 156121914 25925925690]

v.1.6.0
user=> (let [num (* 1234567890 21)] [num (int num) (long num)])
IllegalArgumentException Value out of range for int: 25925925690