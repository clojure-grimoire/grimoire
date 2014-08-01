;; a recursive function to calculate length
;; same as 'count'
(defn length [lst]
    (condp = lst
        (list) 0 ; if empty list result 0
        (+ 1 (length (rest lst))))) ; default expression

user=> (length '(1 2 3))

user=> 3