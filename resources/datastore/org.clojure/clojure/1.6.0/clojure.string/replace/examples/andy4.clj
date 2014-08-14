user=> (= Double/NaN Double/NaN)  ; this is normal
false
user=> (def s1 #{1.0 2.0 Double/NaN})
#'user/s1
user=> s1
#{2.0 1.0 NaN}
user=> (contains? s1 1.0)         ; this is expected
true
user=> (contains? s1 Double/NaN)  ; this might surprise you
false
