; Multiple sequences results in a Cartesian cross of their values.
user=> (doseq [a [1 2]
               b [3 4]]
         (println a b))
1 3
1 4
2 3
2 4
nil