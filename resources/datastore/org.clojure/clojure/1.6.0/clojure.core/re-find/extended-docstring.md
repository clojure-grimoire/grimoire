(re-find regex str) is a pure function that returns the results of
the first match only.  See re-seq if you want a sequence of all
matches.  (re-find matcher) mutates the matcher object.

See docs for function subs, section 'Memory use warning'.
