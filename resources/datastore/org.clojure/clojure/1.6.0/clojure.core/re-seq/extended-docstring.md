(re-seq regex s) is the same as (re-find regex s), except that re-seq
returns a sequence of all matches, not only the first match.  It
returns nil if there were no matches.  Capture groups are handled the
same way as for re-find.
