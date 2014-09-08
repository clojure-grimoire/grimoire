user=> (reductions + [1 1 1 1])
(1 2 3 4)
user=> (reductions + [1 2 3])
(1 3 6)

;; This is just like reduce except that the calculation is collected during the reduce.
user=> (assert (= (reduce + [1 2 3]) 
                  (last (reductions + [1 2 3]))))
nil
