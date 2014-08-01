(defprotocol Fly
  "A simple protocol for flying"
  (fly [this] "Method to fly"))

(defrecord Bird [name species]
  Fly
  (fly [this] (str (:name this) " flies...")))

(extends? Fly Bird)
-> true

(def crow (Bird. "Crow" "Corvus corax"))

(fly crow)
-> "Crow flies..."