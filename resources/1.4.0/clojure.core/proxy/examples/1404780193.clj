;; You can, however, provide multiple-arity functions to get some support 
;; for overloading
user> (let [p (proxy [java.io.InputStream] []
          (read ([] 1)
            ([^bytes bytes] 2)
            ([^bytes bytes off len] 3)))]
  (println (.read p))
  (println (.read p (byte-array 3)))
  (println (.read p (byte-array 3) 0 3)))

1
2
3
nil