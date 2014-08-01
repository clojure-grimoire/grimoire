;; from Stuart Halloway's examples:

(defprotocol Player
  (choose [p])
  (update-strategy [p me you]))

(defrecord Stubborn [choice]
  Player
  (choose [_] choice)
  (update-strategy [this _ _] this))

(defrecord Mean [last-winner]
  Player
  (choose [_]
          (if last-winner
            last-winner
            (random-choice)))
  (update-strategy [_ me you]
                   (->Mean (when (iwon? me you) me))))
