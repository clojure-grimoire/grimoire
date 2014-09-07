user=> (require '[clojure.zip :as zip])
nil
user=> (def zz (zip/seq-zip '(a b (c d e) (f (g h) i) j)))
#'user/zz
user=> zz
[(a b (c d e) (f (g h) i) j) nil]