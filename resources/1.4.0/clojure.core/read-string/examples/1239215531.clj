;convert a string representing a sequence,
;to the sequence that the string represents
user=> (read-string "(\\( \\x \\y \\) \\z)")
(\( \x \y \) \z)

;then you can convert to the string that the string-sequence represents
user=> (apply str (read-string "(\\( \\x \\y \\) \\z)"))
"(xy)z"

;which is the inverse of
user=> (str (first (list (seq "(xy)z"))))
"(\\( \\x \\y \\) \\z)"