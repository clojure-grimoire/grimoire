user=> (clojure.string/split "q1w2e3r4t5y6u7i8o9p0" #"\d+")
["q" "w" "e" "r" "t" "y" "u" "i" "o" "p"]
user=> (clojure.string/split "q1w2e3r4t5y6u7i8o9p0" #"\d+" 5)
["q" "w" "e" "r" "t5y6u7i8o9p0"]
