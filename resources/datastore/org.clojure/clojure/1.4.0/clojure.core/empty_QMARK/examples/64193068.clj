user=> (every? empty? ["" [] () '() {} #{} nil])
true

;example of recommended idiom for testing if not empty
user=> (every? seq ["1" [1] '(1) {:1 1} #{1}])
true