;; It might be easier to think about the producer function as a function
;; that, given element n, produces element n+1 via a recursive call to 
;; itself, wrapped with lazy-seq to delay its execution
;; We might also provide no-argument version of the function that calls 
;; itself for the first element(s) of the sequence being generated.
;; => variant of fibonaci with a no-arg version and using cons first:
(defn sum-last-2 
   ([] (sum-last-2 1 2)) 
   ([n m] (cons n (lazy-seq (sum-last-2 m (+ n m))))))

user=> (take 6 (sum-last-2))
(1 2 3 5 8 13)