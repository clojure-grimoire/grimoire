user=> (refer 'clojure.string
              :rename '{capitalize cap, trim trm})
WARNING: replace already refers to: #'clojure.core/replace in namespace: user, being replaced by: #'clojure.string/replace
WARNING: reverse already refers to: #'clojure.core/reverse in namespace: user, being replaced by: #'clojure.string/reverse
nil

user=> (cap (trm " hOnduRAS  "))
"Honduras"

user=> (join \, [1 2 3])
"1,2,3"