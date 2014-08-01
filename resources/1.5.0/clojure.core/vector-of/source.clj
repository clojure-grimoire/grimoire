(defn vector-of 
  "Creates a new vector of a single primitive type t, where t is one
  of :int :long :float :double :byte :short :char or :boolean. The
  resulting vector complies with the interface of vectors in general,
  but stores the values unboxed internally.

  Optionally takes one or more elements to populate the vector."
  {:added "1.2"
   :arglists '([t] [t & elements])}
  ([t]
   (let [am ^clojure.core.ArrayManager (ams t)]
     (Vec. am 0 5 EMPTY-NODE (.array am 0) nil)))
  ([t x1]
   (let [am ^clojure.core.ArrayManager (ams t)
         arr (.array am 1)]
     (.aset am arr 0 x1)
     (Vec. am 1 5 EMPTY-NODE arr nil)))
  ([t x1 x2]
   (let [am ^clojure.core.ArrayManager (ams t)
         arr (.array am 2)]
     (.aset am arr 0 x1)
     (.aset am arr 1 x2)
     (Vec. am 2 5 EMPTY-NODE arr nil)))
  ([t x1 x2 x3]
   (let [am ^clojure.core.ArrayManager (ams t)
         arr (.array am 3)]
     (.aset am arr 0 x1)
     (.aset am arr 1 x2)
     (.aset am arr 2 x3)
     (Vec. am 3 5 EMPTY-NODE arr nil)))
  ([t x1 x2 x3 x4]
   (let [am ^clojure.core.ArrayManager (ams t)
         arr (.array am 4)]
     (.aset am arr 0 x1)
     (.aset am arr 1 x2)
     (.aset am arr 2 x3)
     (.aset am arr 3 x4)
     (Vec. am 4 5 EMPTY-NODE arr nil)))
  ([t x1 x2 x3 x4 & xn]
   (loop [v  (vector-of t x1 x2 x3 x4)
          xn xn]
     (if xn
       (recur (.cons v (first xn)) (next xn))
       v))))