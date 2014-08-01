(defmacro proxy
  "class-and-interfaces - a vector of class names

  args - a (possibly empty) vector of arguments to the superclass
  constructor.

  f => (name [params*] body) or
  (name ([params*] body) ([params+] body) ...)

  Expands to code which creates a instance of a proxy class that
  implements the named class/interface(s) by calling the supplied
  fns. A single class, if provided, must be first. If not provided it
  defaults to Object.

  The interfaces names must be valid interface types. If a method fn
  is not provided for a class method, the superclass methd will be
  called. If a method fn is not provided for an interface method, an
  UnsupportedOperationException will be thrown should it be
  called. Method fns are closures and can capture the environment in
  which proxy is called. Each method fn takes an additional implicit
  first arg, which is bound to 'this. Note that while method fns can
  be provided to override protected methods, they have no other access
  to protected members, nor to super, as these capabilities cannot be
  proxied."
  {:added "1.0"}
  [class-and-interfaces args & fs]
   (let [bases (map #(or (resolve %) (throw (Exception. (str "Can't resolve: " %)))) 
                    class-and-interfaces)
         [super interfaces] (get-super-and-interfaces bases)
         compile-effect (when *compile-files*
                          (let [[cname bytecode] (generate-proxy super interfaces)]
                            (clojure.lang.Compiler/writeClassFile cname bytecode)))
         pc-effect (apply get-proxy-class bases)
         pname (proxy-name super interfaces)]
     ;remember the class to prevent it from disappearing before use
     (intern *ns* (symbol pname) pc-effect)
     `(let [;pc# (get-proxy-class ~@class-and-interfaces)
            p# (new ~(symbol pname) ~@args)] ;(construct-proxy pc# ~@args)]   
        (init-proxy p#
         ~(loop [fmap {} fs fs]
            (if fs
              (let [[sym & meths] (first fs)
                    meths (if (vector? (first meths))
                            (list meths)
                            meths)
                    meths (map (fn [[params & body]]
                                   (cons (apply vector 'this params) body))
                               meths)]
                (if-not (contains? fmap (name sym))		  
                (recur (assoc fmap (name sym) (cons `fn meths)) (next fs))
		           (throw (IllegalArgumentException.
			              (str "Method '" (name sym) "' redefined")))))
              fmap)))
        p#)))