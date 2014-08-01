user=> (doseq [[k v] (map identity {:1 1 :2 2 :3 3})] 
         (prn k v))
:1 1
:2 2
:3 3
nil

;; where
user=> (map identity {:1 1 :2 2 :3 3})
([:1 1] [:2 2] [:3 3])

;; or simply
user=> (doseq [[k v] {:1 1 :2 2 :3 3}]
         (prn k v))
:1 1
:3 3
:2 2
nil