;; simple examples

user=> (char-escape-string \newline)
"\\n"
user=> (char-escape-string \c) ; no escape sequence for 'c'
nil
user=> (char-escape-string \tab)
"\\t"
user=> (char-escape-string \backspace)
"\\b"
user=>