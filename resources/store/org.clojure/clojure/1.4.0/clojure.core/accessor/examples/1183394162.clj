(defstruct car-struct :make :model :year :color)

(def car (struct car-struct "Toyota" "Prius" 2010))

(def make (accessor car-struct :make))

user=> (make car)  ; Same as both (car :make) and (:make car)
"Toyota"           

