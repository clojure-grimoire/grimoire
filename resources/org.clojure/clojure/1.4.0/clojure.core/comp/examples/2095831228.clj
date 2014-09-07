user=> (def countif (comp count filter))
#'user/countif
user=> (countif even? [2 3 1 5 4])
2