(defn get-proxy-class 
  "Takes an optional single class followed by zero or more
  interfaces. If not supplied class defaults to Object.  Creates an
  returns an instance of a proxy class derived from the supplied
  classes. The resulting value is cached and used for any subsequent
  requests for the same class set. Returns a Class object."
  {:added "1.0"}
  [& bases]
    (let [[super interfaces] (get-super-and-interfaces bases)
          pname (proxy-name super interfaces)]
      (or (RT/loadClassForName pname)
          (let [[cname bytecode] (generate-proxy super interfaces)]
            (. ^DynamicClassLoader (deref clojure.lang.Compiler/LOADER) (defineClass pname bytecode [super interfaces]))))))