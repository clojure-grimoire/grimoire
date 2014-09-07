(defmulti 
  simple-dispatch
  "The pretty print dispatch function for simple data structure format."
  {:added "1.2" :arglists '[[object]]} 
  class)