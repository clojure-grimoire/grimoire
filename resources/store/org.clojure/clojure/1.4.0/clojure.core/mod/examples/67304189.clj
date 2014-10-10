
user=> (mod 10 5)
0

user=> (mod 10 6)
4

user=> (mod 10 10)
0

user=> (mod 10 -1)
0

;; The mod function is defined as the amount by which a number exceeds the largest integer multiple of the divisor that is not greater than that number.
;; The largest integer multiple of 5 not greater than -2 is 5 * -1 = -5. The amount by which -2 exceeds -5 is 3. 
;;
user=> (mod -2  5) 
3
