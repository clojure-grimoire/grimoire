(re-seq regex s) is the same as (re-find regex s), except that re-seq
returns a sequence of all matches, not only the first match.  It
returns nil if there were no matches.  Capture groups are handled the
same way as for re-find.

    user=> (re-seq #"\d" "Mac OS X 10.6.8")
    ("1" "0" "6" "8")
    user=> (re-seq #"\d+" "Mac OS X 10.6.8")
    ("10" "6" "8")
    user=> (re-seq #"ZZ" "Mac OS X 10.6.8")
    nil

    ;; Capture groups in the regex cause each returned match to be a
    ;; vector of matches.  See re-find for more examples.
    user=> (re-seq #"\S+:\d+" " RX pkts:18 err:5 drop:48")
    ("pkts:18" "err:5" "drop:48")
    user=> (re-seq #"(\S+):(\d+)" " RX pkts:18 err:5 drop:48")
    (["pkts:18" "pkts" "18"] ["err:5" "err" "5"] ["drop:48" "drop" "48"])

See also: re-find, re-matches, re-pattern, clojure.string/replace,
clojure.string/replace-first, re-matcher, re-groups

See docs for function subs, section 'Memory use warning'.
