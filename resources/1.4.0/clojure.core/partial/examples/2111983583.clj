; Maps exponent to coefficient
; x^3 + 2x + 1
(def poly (fn [n]
			(cond
				(= 0 n) 1
				(= 1 n) 2
				(= 3 n) 1
				:else 0)
			)
)

; Differentiates input by returning a polynomial that is curried
; 3x^2 + 2
(defn diff [p]
		(partial (fn [p n] (* (+ 1 n) (p (+ 1 n)))) p)
	)

(poly 3)
;=> 1
((diff poly) 3)
;=> 0
((diff poly) 2)
;=> 3
