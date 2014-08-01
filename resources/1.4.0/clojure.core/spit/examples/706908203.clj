user=> (spit "flubber.txt" "test")
nil
user=> (slurp "flubber.txt")
"test"