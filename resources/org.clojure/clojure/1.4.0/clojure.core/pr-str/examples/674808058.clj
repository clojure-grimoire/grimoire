;; you can think of pr-str as the inverse of read-string
;; turn string into symbols
user=> (read-string "(a b foo :bar)")
(a b foo :bar)

;;turn symbols into a string
user=> (pr-str '(a b foo :bar))
"(a b foo :bar)"