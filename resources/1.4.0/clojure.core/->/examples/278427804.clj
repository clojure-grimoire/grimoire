;; Your own REPL! (Read Eval Print Loop)

;; We would need a little helper macro for that
;; It does what its name says - loops forever
user=> (defmacro loop-forever [& body] `(loop [] ~@body (recur)))

;; Your own REPL
user=> (loop-forever (println (eval (read))))                                                                     
(+ 1 2)
3

;; If you read the above code left to right (outside in) it reads LPER.
;; Inside out it reads REPL alright.

;; Sometimes it might be easier to read code outside in, just like a sequence of steps:
;; 1. Read, 2. Eval, 3. Print, 4. Loop
;; Here's how -> helps you:

user=> (-> (read) (eval) (println) (loop-forever))                                                                
(+ 1 2)
3

;; Does that read easier for you? If it does, -> is your friend!

;; To see what Clojure did behind the scenes with your -> expression:
user=> (require 'clojure.walk)
nil
user=> (clojure.walk/macroexpand-all '(-> (read) (eval) (println) (loop-forever)))
(loop* [] (println (eval (read))) (recur))

;; You can even use ->'s cousin ->> to setup your own REPL:
user=> (->> (read) (eval) (println) (while true))
(+ 1 2)
3

;; Can you see why we can't use -> to write the above?

