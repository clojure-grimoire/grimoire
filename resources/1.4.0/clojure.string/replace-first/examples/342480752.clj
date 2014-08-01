user=> (use '[clojure.string :only (replace-first)])

;; Only replace the first match.
user=> (replace-first "A good day to you, sir.  Good day." #"day" "night")
"A good night to you, sir.  Good day."

;; If there are no matches, return the original string.
user=> (replace-first "A good day to you, sir." #"madam" "master")
"A good day to you, sir."

;; (?i) at the beginning of a pattern makes the entire thing match
;; case-insensitively, at least for US ASCII characters.  (?u) does
;; the corresponding thing for Unicode.
user=> (replace-first "Day need not be SHOUTED." #"(?i)day" "night")
"night need not be SHOUTED."

;; See here for many details on regex patterns:
;; http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
;; Also the book "Mastering Regular Expressions" by Jeffrey Friedl.
