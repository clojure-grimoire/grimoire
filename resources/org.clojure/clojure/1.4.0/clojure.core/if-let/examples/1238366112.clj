user=> (defn sum-even-numbers [nums]
         (if-let [nums (seq (filter even? nums))]
           (reduce + nums)
           "No even numbers found."))
#'user/sum-even-numbers

user=> (sum-even-numbers [1 3 5 7 9])
"No even numbers found."

user=> (sum-even-numbers [1 3 5 7 9 10 12])
22
