user=> (decimal? 1)
false

user=> (decimal? 1.0)
false

user=> (decimal? 1M)
true

user=> (decimal? 99999999999999999999999999999999999)
false

user=> (decimal? 1.0M)
true