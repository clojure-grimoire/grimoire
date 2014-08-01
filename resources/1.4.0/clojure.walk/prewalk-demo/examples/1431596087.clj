(use 'clojure.walk)

user> (prewalk-demo [[1 2] [3 4 [5 6]] [7 8]])
Walked: [[1 2] [3 4 [5 6]] [7 8]]
Walked: [1 2]
Walked: 1
Walked: 2
Walked: [3 4 [5 6]]
Walked: 3
Walked: 4
Walked: [5 6]
Walked: 5
Walked: 6
Walked: [7 8]
Walked: 7
Walked: 8
[[1 2] [3 4 [5 6]] [7 8]]

user> (prewalk-demo {:a 1 :b 2})
Walked: {:a 1, :b 2}
Walked: [:a 1]
Walked: :a
Walked: 1
Walked: [:b 2]
Walked: :b
Walked: 2
{:a 1, :b 2}