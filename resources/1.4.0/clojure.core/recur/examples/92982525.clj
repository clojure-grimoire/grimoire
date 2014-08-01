; A loop that sums the numbers 10 + 9 + 8 + ...

; Set initial values count (cnt) from 10 and down
(loop [sum 0 cnt 10]
    ; If count reaches 0 then exit the loop and return sum
    (if (= cnt 0)
    sum
    ; Otherwise add count to sum, decrease count and 
    ; use recur to feed the new values back into the loop
    (recur (+ cnt sum) (dec cnt))))