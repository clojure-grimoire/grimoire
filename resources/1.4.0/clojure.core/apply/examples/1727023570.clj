;; You can use map and apply together to drill one level deep in a collection
;; of collections, in this case returning a collection of the max of each
;; nested collection

user=> (map #(apply max %) [[1 2 3][4 5 6][7 8 9]])
(3 6 9)