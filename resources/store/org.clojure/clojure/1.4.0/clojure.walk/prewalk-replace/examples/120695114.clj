user=> (clojure.walk/prewalk-replace '{a b} '(c (d a)))
(c (d b))