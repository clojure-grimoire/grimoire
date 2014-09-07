user=> (def large-vec (vec (range 0 10000)))
#'user/large-vec

user=> (time (last large-vec))
"Elapsed time: 1.279841 msecs"
9999

user=> (time (peek large-vec))
"Elapsed time: 0.049238 msecs"
9999
