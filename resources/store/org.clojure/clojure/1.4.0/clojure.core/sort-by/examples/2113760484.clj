user=> (sort-by count ["aaa" "bb" "c"])
("c" "bb" "aaa")

user=> (sort-by first [[1 2] [2 2] [2 3]])   
([1 2] [2 2] [2 3])

user=> (sort-by first > [[1 2] [2 2] [2 3]])   
([2 2] [2 3] [1 2])