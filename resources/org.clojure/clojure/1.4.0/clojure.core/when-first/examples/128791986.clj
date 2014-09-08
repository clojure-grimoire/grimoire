user=> (when-first [a [1 2 3]] a)
1
user=> (when-first [a []] a)
nil
user=> (when-first [a nil] a)
nil