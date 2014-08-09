user=> (re-seq #"\d" "Mac OS X 10.6.8")
("1" "0" "6" "8")
user=> (re-seq #"\d+" "Mac OS X 10.6.8")
("10" "6" "8")
user=> (re-seq #"ZZ" "Mac OS X 10.6.8")
nil
