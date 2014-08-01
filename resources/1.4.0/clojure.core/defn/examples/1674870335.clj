;; You can use destructuring to have keyword arguments. This would be a
;; pretty verbose version of map (in an example a bit more verbose than
;; the first above):

(defn keyworded-map [& {function :function sequence :sequence}]
  (map function sequence))

;; You can call it like this:

user=> (keyworded-map :sequence [1 2 3] :function #(+ % 2))
(3 4 5)


;; The declaration can be shortened with ":keys" if your local variables 
;; should be named in the same way as your keys in the map:

(defn keyworded-map [& {:keys [function sequence]}]
  (map function sequence))
