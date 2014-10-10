(def vz (vector-zip [1 2 3 4 5]))

;; root
;;  |________
;;  | | | | |
;;  1 2[3]4 5

;;go down and then right twice, what's to the left of me now?
(-> vz down right right lefts)
=>(1 2)

;;equivalent:
(-> vz down right right rights)
=>(4 5)

