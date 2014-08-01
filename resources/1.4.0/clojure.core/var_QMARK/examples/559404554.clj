(def my-symbol)
(var? #'my-symbol)
=> true

(var? (var my-symbol))
=> true

(var? (def my-symbol2))
=> true