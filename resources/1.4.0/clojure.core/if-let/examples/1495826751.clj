; This macro is nice when you need to calculate something big. And you need 
; to use the result but only when it's true:

(if-let [life (meaning-of-life 12)]
   life
   (if-let [origin (origin-of-life 1)]
      origin
      (if-let [shot (who-shot-jr 5)]
         block-sol
	 42)))

; As you can see in the above example it will return the answer 
; to the question only if the answer is not nil. If the answer
; is nil it will move to the next question. Until finally it
; gives up and returns 42.