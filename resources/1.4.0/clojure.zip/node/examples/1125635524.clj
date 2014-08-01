user=>(require '[clojure.zip :as zip])
nil
user=> (def original [1 '(a b c) 2])
#'user/original
user=> (def root-loc (zip/seq-zip (seq original)))
#'user/root-loc

user=> (zip/node (zip/down root-loc))
1
