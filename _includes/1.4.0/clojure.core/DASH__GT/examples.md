### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Use of `->` (the "thread-first" macro) can help make code
;; more readable by removing nesting. It can be especially
;; useful when using host methods:

;; Arguably a bit cumbersome to read:
user=> (first (.split (.replace (.toUpperCase "a b c d")
                                "A"
                                "X")
                      " "))
"X"

;; Perhaps easier to read:
user=> (-> "a b c d"
           .toUpperCase
           (.replace "A" "X")
           (.split " ")
           first)
"X"

;; It can also be useful for pulling values out of deeply-nested
;; data structures:
user=> (def person
            {:name "Mark Volkmann"
             :address {:street "644 Glen Summit"
                       :city "St. Charles"
                       :state "Missouri"
                       :zip 63304}
             :employer {:name "Object Computing, Inc."
                        :address {:street "12140 Woodcrest Dr."
                                  :city "Creve Coeur"
                                  :state "Missouri"
                                  :zip 63141}}})

user=> (-> person :employer :address :city)
"Creve Coeur"

;; same as above, but with more nesting
user=> (((person :employer) :address) :city)
"Creve Coeur"

;; Note that this operator (along with ->>) has at times been
;; referred to as a 'thrush' operator.

;; From http://clojure-examples.appspot.com/clojure.core/-%3E
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
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

{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (def c 5)
user=> (-> c (+ 3) (/ 2) (- 1))
3

;; and if you are curious why
user=> (use 'clojure.walk)
user=> (macroexpand-all '(-> c (+ 3) (/ 2) (- 1)))
(- (/ (+ c 3) 2) 1)
{% endraw %}
{% endhighlight %}


