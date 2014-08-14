;; merge two maps using the addition function

user=> (merge-with + 
                   {:a 1  :b 2}
                   {:a 9  :b 98 :c 0})
    
{:c 0, :a 10, :b 100}