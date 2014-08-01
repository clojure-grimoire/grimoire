(defn position [x coll & {:keys [from-end all] :or {from-end false all false}}]
  (cond
   (true? from-end) (last (keep-indexed #(if (= x %2) %1) coll))
   (true? all) (keep-indexed #(if (= x %2) %1) coll)
   :else (first (keep-indexed #(if (= x %2) %1) coll))))

user> (position [1 1] [[1 0][1 1][2 3][1 1]])
1
user> (position [1 1] [[1 0][1 1][2 3][1 1]] :from-end true)
3
user> (position [1 1] [[1 0][1 1][2 3][1 1]] :all true)
(1 3)

user> (def foo (shuffle (range 10)))
#'user/foo
user> foo
(5 8 9 1 2 7 0 6 3 4)
user> (position 5 foo)
0
user> (position 0 foo)
6