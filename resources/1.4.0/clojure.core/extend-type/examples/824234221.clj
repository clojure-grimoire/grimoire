;;; This is a library for the shopping result.

(defrecord Banana [qty])

;;; 'subtotal' differ from each fruit.

(defprotocol Fruit
  (subtotal [item]))

(extend-type Banana
  Fruit
  (subtotal [item]
    (* 158 (:qty item))))

;;; Please see the term of 'reify'.