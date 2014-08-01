(def factorial
  (fn [n]
    (loop [cnt n acc 1]
       (if (zero? cnt)
            acc
          (recur (dec cnt) (* acc cnt))
; in loop cnt will take the value (dec cnt)
; and acc will take the value (* acc cnt)
))))