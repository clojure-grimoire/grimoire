user=> (defn f [n] (* n n n))
#'user/f
user=> ((ns-resolve *ns* (symbol "f")) 10)
1000