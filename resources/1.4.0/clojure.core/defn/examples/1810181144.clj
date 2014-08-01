(defn somefn
  [req1 req2 ;required params
   & {:keys [a b c d e] ;optional params
      :or {a 1 ;optional params with preset default values other than the nil default
                  ; b takes nil if not specified on call
            c 3 ; c is 3 when not specified on call
            d 0 ; d is 0 --//--
                  ; e takes nil if not specified on call
           }
      :as mapOfParamsSpecifiedOnCall ;takes nil if no extra params(other than the required ones) are specified on call
      }]
  (println req1 req2 mapOfParamsSpecifiedOnCall a b c d e)
  )

=> (somefn 9 10 :b 2 :d 4)
;9 10 {:b 2, :d 4} 1 2 3 4 nil
nil
=> (somefn)
;ArityException Wrong number of args (0) passed to: funxions$somefn  ;clojure.lang.AFn.throwArity (AFn.java:437)
=> (somefn 9 10)
;9 10 nil 1 nil 3 0 nil
nil
=> (somefn 9 10 :x 123)
;9 10 {:x 123} 1 nil 3 0 nil
nil
=> (somefn 9 10 123)
;IllegalArgumentException No value supplied for key: 123  ;clojure.lang.PersistentHashMap.create (PersistentHashMap.java:77)
=> (somefn 9 10 123 45)
;9 10 {123 45} 1 nil 3 0 nil
nil
=> (try 
     (somefn 9 10 123)
     (catch IllegalArgumentException e (println "caught:" e)))
;caught: #<IllegalArgumentException java.lang.IllegalArgumentException: No value supplied for key: 123>
nil