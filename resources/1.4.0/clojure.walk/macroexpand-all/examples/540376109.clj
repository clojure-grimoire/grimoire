user=> (use 'clojure.walk)

; tryclj.com and lazybot on #clojure get the following wrong
user=> (let [-> inc] (-> 5)) 
6

; Below macroexpansion is supposed to result in equivalent code to the above
user=> (macroexpand-all '(let [-> inc] (-> 5)))
(let* [-> inc] 5)
user=> (let* [-> inc] 5)
5

; However, as is clear above, it does not