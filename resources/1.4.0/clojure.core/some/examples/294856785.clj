;; find a whether a word is in a list of words.
(def word "foo")
(some (partial = word) words)