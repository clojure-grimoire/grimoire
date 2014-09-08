(defn pos-neg-or-zero
  "Determines whether or not n is positive, negative, or zero"
  [n]
  (cond
    (< n 0) "negative"
    (> n 0) "positive"
    :else "zero"))

user=> (pos-neg-or-zero 5)
"positive"
user=> (pos-neg-or-zero -1)
"negative"
user=> (pos-neg-or-zero 0)
"zero"
