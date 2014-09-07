;; An example of using the "thread-last" macro to get
;; the sum of the first 10 even squares.
user=> (->> (range)
            (map #(* % %))
            (filter even?)
            (take 10)
            (reduce +))
1140

;; This expands to:
user=> (reduce +
               (take 10
                     (filter even?
                             (map #(* % %)
                                  (range)))))
1140
