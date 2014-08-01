;; 'merge-with' works with an arbitrary number of maps:

user=> (merge-with + 
                   {:a 1  :b 2}
                   {:a 9  :b 98  :c 0}
                   {:a 10 :b 100 :c 10}
                   {:a 5}
                   {:c 5  :d 42})
    
{:d 42, :c 15, :a 25, :b 200}