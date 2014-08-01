;; you can think of read-string as the inverse of pr-str
;; turn string into symbols
user=> (read-string "(a b foo :bar)")
(a b foo :bar)

;;turn symbols into a string
user=> (pr-str '(a b foo :bar))
"(a b foo :bar)"