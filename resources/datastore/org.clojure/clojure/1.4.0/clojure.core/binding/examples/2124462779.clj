;;Use t like a "template"

(declare ^:dynamic t)

(defn addt [] 
  (+ t 10))

(binding [t 1]
  (addt))
=> 11