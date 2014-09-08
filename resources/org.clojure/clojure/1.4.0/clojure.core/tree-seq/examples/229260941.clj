;; Each node is a (node-root child1 child2 ...),
;; so branch?==next and children==rest
;;
;;     A
;;    / \
;;   B   C
;;  / \  |
;; D   E F
;;
user=> (map first (tree-seq next rest '(:A (:B (:D) (:E)) (:C (:F)))))

(:A :B :D :E :C :F)