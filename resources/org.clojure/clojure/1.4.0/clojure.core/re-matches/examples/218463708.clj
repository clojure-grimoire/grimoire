; Regex match flags can be embedded in the regex string.  So, we can convert the normal case-sensitive matching into case-insensitive matching.

user=> (re-matches #"hello" "HELLO")       ; case-sensitive
nil

user=> (re-matches #"(?i)hello" "hello")   ; case-insensitive
"hello"
user=> (re-matches #"(?i)hello" "HELLO")   ; case-insensitive
"HELLO"
user=> (re-matches #"(?i)HELLO" "heLLo")   ; case-insensitive
"heLLo"
