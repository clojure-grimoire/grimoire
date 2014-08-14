;; Parenthesized groups in the regex cause each returned match to be a
;; vector of matched strings.  See re-find for more examples.
user=> (def line " RX pkts:18 err:5 drop:48")
#'user/line

user=> (re-seq #"(\S+):(\d+)" line)
(["pkts:18" "pkts" "18"] ["err:5" "err" "5"] ["drop:48" "drop" "48"])
