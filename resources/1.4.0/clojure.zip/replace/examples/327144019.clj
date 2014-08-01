(use 'clojure.zip)

(def vz (vector-zip [1 2 [73 88] 4]))

(root (replace (-> vz down right right) 3))
=>[1 2 3 4]