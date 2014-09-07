;; Capture groups in the regex cause each returned match to be a
;; vector of matches.  See re-find for more examples.
user=> (re-seq #"\S+:\d+" " RX pkts:18 err:5 drop:48")
("pkts:18" "err:5" "drop:48")
user=> (re-seq #"(\S+):(\d+)" " RX pkts:18 err:5 drop:48")
(["pkts:18" "pkts" "18"] ["err:5" "err" "5"] ["drop:48" "drop" "48"])
