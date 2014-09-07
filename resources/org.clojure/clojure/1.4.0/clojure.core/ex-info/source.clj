(defn ex-info
  "Alpha - subject to change.
   Create an instance of ExceptionInfo, a RuntimeException subclass
   that carries a map of additional data."
  {:added "1.4"}
  ([msg map]
     (ExceptionInfo. msg map))
  ([msg map cause]
     (ExceptionInfo. msg map cause)))