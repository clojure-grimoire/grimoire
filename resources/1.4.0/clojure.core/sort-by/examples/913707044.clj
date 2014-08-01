(def x [{:foo 2 :bar 11}
        {:bar 99 :foo 1}
        {:bar 55 :foo 2}
        {:foo 1 :bar 77}])

;sort by :foo, and where :foo is equal, sort by :bar?
(sort-by (juxt :foo :bar) x)
;=>({:foo 1, :bar 77} {:bar 99, :foo 1} {:foo 2, :bar 11} {:bar 55, :foo 2})