;; Given you have a function that will read from *in*
(defn prompt [question]
  (println question)
  (read-line))

user=> (prompt "How old are you?")
How old are you?
34                   ; <== This is what you enter
"34"                 ; <== This is returned by the function

;; You can now simulate entering your age at the prompt by using with-in-str

user=> (with-in-str "34" (prompt "How old are you?"))
How old are you?
"34"                 ; <== The function now returns immediately 
