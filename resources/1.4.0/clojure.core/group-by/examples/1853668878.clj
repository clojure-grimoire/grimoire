user=> (group-by count ["a" "as" "asd" "aa" "asdf" "qwer"])
{1 ["a"], 2 ["as" "aa"], 3 ["asd"], 4 ["asdf" "qwer"]}

user=> (group-by odd? (range 10))
{false [0 2 4 6 8], true [1 3 5 7 9]}
