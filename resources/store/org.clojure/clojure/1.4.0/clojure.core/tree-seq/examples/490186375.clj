;; Each node is a number or a seq, 
;; so branch?==seq? and children==identity
;; 
;;     .
;;    / \
;;   .   .
;;  /|\  |
;; 1 2 . 4
;;     |  
;;     3
;;

user=> (tree-seq seq? identity '((1 2 (3)) (4)))

(((1 2 (3)) (4)) (1 2 (3)) 1 2 (3) 3 (4) 4)
