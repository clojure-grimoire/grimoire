user=> (let [x 2]
         `(1 x 3))
(1 user/x 3)

user=> (let [x 2]
         `(1 ~x 3))
(1 2 3)
