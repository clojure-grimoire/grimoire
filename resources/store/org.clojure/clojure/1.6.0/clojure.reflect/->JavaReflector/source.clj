(deftype JavaReflector [classloader]
  Reflector
  (do-reflect [_ typeref]
           (let [cls (Class/forName (typename typeref) false classloader)]
             {:bases (not-empty (set (map typesym (bases cls))))
              :flags (parse-flags (.getModifiers cls) :class)
              :members (set/union (declared-fields cls)
                                  (declared-methods cls)
                                  (declared-constructors cls))})))