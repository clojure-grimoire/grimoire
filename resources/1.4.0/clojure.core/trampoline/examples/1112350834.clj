user> (defn foo [x]
        (if (< x 0)
          (println "done")
          #(foo (do (println :x x) (dec x)))))
#'user/foo

;; trampoline will keep calling the function for as long as "foo" returns a function.
user> (trampoline foo 10)
:x 10
:x 9
:x 8
:x 7
:x 6
:x 5
:x 4
:x 3
:x 2
:x 1
:x 0
done
nil