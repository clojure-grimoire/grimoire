user=> (def *matcher* (re-matcher #"\d+" "abc12345def"))
#'user/*matcher*

user=> (re-find *matcher*)
"12345"