
user=> (some true? [false false false])
nil
user=> (some true? [false true false])
true
user=> (some true? [true true true])
true
