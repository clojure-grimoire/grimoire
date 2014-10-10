(defmacro unless [pred a b]
  `(if (not ~pred) ~a ~b))

;; usage:

(unless false (println "Will print") (println "Will not print"))