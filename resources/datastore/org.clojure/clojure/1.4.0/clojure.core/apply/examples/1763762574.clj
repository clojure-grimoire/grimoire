;; If you were to try
user=> (max [1 2 3])
[1 2 3]

;; You would get '[1 2 3]' for the result. In this case, 'max' has received one
;; vector argument, and the largest of its arguments is that single vector.

;; If you would like to find the largest item **within** the vector, you would need
;; to use `apply`

user=> (apply max [1 2 3])
3

;; which is the same as (max 1 2 3)
