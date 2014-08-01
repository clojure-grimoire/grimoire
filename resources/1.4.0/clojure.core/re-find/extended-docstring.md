(re-find regex str) is a pure function that returns the results of
the first match only.  See re-seq if you want a sequence of all
matches.  (re-find matcher) mutates the matcher object.

If there are no parenthesized 'groups' in the regex, re-find either
returns the substring of s that matches, or nil if there is no match.
It also behaves this way if all parenthesized groups do not 'capture',
because they begin with ?:

    user=> (re-find #"\d+" "abc123def")
    "123"
    user=> (re-find #"\d+" "abcdef")
    nil
    user=> (re-find #"(?:\d+)" "abc123def")
    "123"

If there are capturing groups, then on a match re-find returns a
vector where the first element is the string that matches the entire
regex, and successive vector elements are either strings matching a
capture group, or nil if nothing matched that capture group.  Groups
are ordered in the same way that their left parentheses occur in the
string.

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

See also: re-seq, re-matches, re-pattern, clojure.string/replace,
clojure.string/replace-first, re-matcher, re-groups

See docs for function subs, section 'Memory use warning'.
