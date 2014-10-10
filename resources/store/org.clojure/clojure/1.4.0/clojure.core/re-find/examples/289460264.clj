;; When there are parenthesized groups in the pattern and re-find
;; finds a match, it returns a vector.  The first item is the part of
;; the string that matches the entire pattern, and each successive
;; item are the parts of the string that matched the 1st, 2nd,
;; etc. parenthesized groups.  Groups are numbered by the order in
;; which their left parenthesis occurs in the string, from left to
;; right.
user=> (def line " RX packets:1871074138 errors:5 dropped:48 overruns:9")
#'user/line

user=> (re-find #"(\S+):(\d+)" line)
["packets:1871074138" "packets" "1871074138"]

;; groups can nest
user=> (re-find #"(\S+:(\d+)) \S+:\d+" line)
["packets:1871074138 errors:5" "packets:1871074138" "1871074138"]

;; If there is no match, re-find always returns nil, whether there are
;; parenthesized groups or not.
user=> (re-find #"(\S+):(\d+)" ":2 numbers but not 1 word-and-colon: before")
nil

;; A parenthesized group can have nil as its result if it is part of
;; an 'or' (separated by | in the regex), and another alternative is
;; the one that matches.
user=> (re-find #"(\D+)|(\d+)" "word then number 57")
["word then number " "word then number " nil]

user=> (re-find #"(\D+)|(\d+)" "57 number then word")
["57" nil "57"]

;; It is also possible for a group to match the empty string.
user=> (re-find #"(\d*)(\S)\S+" "lots o' digits 123456789")
["lots" "" "l"]

;; If you want to use parentheses to group a part of the regex, but
;; have no interest in capturing the string it matches, you can follow
;; the left paren with ?: to prevent capturing.
user=> (re-find #"(?:\S+):(\d+)" line)
["packets:1871074138" "1871074138"]

;; re-matches also behaves this way, and re-seq returns a sequence of
;; matches, where each one can be a vector like re-find returns.
