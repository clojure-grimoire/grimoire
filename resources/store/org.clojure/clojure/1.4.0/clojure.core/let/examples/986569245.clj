;;; no value of a key
user> (let [{:keys [a b] :as m} (:x {})]
        [a b m])
[nil nil nil]

;;; same as above
user> (let [{:keys [a b] :as m} nil]
        [a b m])
[nil nil nil]

;;; similar case on Vector
user> (let [[a b :as v] nil]
        [a b v])
[nil nil nil]
