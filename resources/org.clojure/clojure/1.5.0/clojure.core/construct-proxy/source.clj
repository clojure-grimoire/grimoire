(defn construct-proxy
  "Takes a proxy class and any arguments for its superclass ctor and
  creates and returns an instance of the proxy."
  {:added "1.0"}
  [c & ctor-args]
    (. Reflector (invokeConstructor c (to-array ctor-args))))