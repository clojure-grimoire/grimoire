;; When there are less than n items in the coll, partition's behaviour
;; depends on whether there is a pad or not

;; without pad
user> (partition 10 [1 2 3 4])
()
;; again, without pad
user> (partition 10 10 [1 2 3 4])
()
;; with a pad this time (note: the pad is an empty sequence)
user> (partition 10 10 nil [1 2 3 4])
((1 2 3 4))
;; or, explicit empty sequence instead of nil
user> (partition 10 10 [] [1 2 3 4])
((1 2 3 4))
