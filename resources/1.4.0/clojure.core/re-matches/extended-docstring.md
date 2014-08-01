(re-matches regex s) is the same as (re-find regex s), except that
re-matches only returns a match result if the regex can be matched
against the entire string.  re-find returns a match if the regex can
be matched against any substring of the given string.

    user=> (re-find #"\d+" "abc123def")
    "123"
    user=> (re-matches #"\d+" "abc123def")
    nil
    user=> (re-matches #"\d+" "123")
    "123"

See the extended docs of re-find for additional examples, and notes on
how the return value is a vector when there are capture groups in the
regex.

See also: re-find, re-seq, re-pattern, clojure.string/replace,
clojure.string/replace-first, re-matcher, re-groups

See docs for function subs, section 'Memory use warning'.
