(defn compute-across [func elements value]
  (if (empty? elements)
    value
    (recur func (rest elements) (func value (first elements)))))

(defn total-of [numbers]
  (compute-across + numbers 0))

(defn larger-of [x y]
  (if (> x y) x y))

(defn greatest-of [numbers]
  (compute-across larger-of numbers (first numbers)))