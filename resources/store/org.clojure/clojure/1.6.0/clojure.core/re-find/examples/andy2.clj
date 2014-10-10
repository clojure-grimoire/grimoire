;; If there are capturing groups, then on a match re-find returns a
;; vector where the first element is the string that matches the entire
;; regex, and successive vector elements are either strings matching a
;; capture group, or nil if nothing matched that capture group.  Groups
;; are ordered in the same way that their left parentheses occur in the
;; string.

user=> (def line " RX packets:1871 errors:5 dropped:48 overruns:9")
#'user/line

user=> (re-find #"(\S+):(\d+)" line)
["packets:1871" "packets" "1871"]

;; groups can nest
user=> (re-find #"(\S+:(\d+)) \S+:\d+" line)
["packets:1871 errors:5" "packets:1871" "1871"]

;; If there is no match, re-find always returns nil, whether there
;; are parenthesized groups or not.
user=> (re-find #"(\S+):(\d+)"
                ":2 numbers but not 1 word-and-colon: before")
nil

;; A capture group can have nil as its result if it is part of an
;; 'or' (separated by | in the regex), and another alternative is
;; the one that matches.

user=> (re-find #"(\D+)|(\d+)" "word then number 57")
["word then number " "word then number " nil]

user=> (re-find #"(\D+)|(\d+)" "57 number then word")
["57" nil "57"]

;; It is also possible for a group to match the empty string.
user=> (re-find #"(\d*)(\S)\S+" "lots o' digits 123456789")
["lots" "" "l"]
