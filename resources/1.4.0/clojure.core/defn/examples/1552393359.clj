user=> (defn bar
         ([a b]   (bar a b 100))
         ([a b c] (* a b c)))
#'user/bar
user=> (bar 5 6)
3000
user=> (bar 5 6 2)
60
