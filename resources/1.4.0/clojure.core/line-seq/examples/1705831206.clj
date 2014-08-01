(import '(java.io BufferedReader StringReader))

;; line terminators are stripped
user=> (line-seq (BufferedReader. (StringReader. "1\n2\n\n3")))
("1" "2" "" "3")

;; empty string gives nil
user=> (line-seq (BufferedReader. (StringReader. "")))
nil
