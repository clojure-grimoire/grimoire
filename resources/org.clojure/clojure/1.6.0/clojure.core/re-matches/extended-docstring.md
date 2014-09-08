(re-matches regex s) is the same as (re-find regex s), except that
re-matches only returns a match result if the regex can be matched
against the entire string.  re-find returns a match if the regex can
be matched against any substring of the given string.

See the extended docs of re-find for additional examples, and notes on
how the return value is a vector when there are capture groups in the
regex.
