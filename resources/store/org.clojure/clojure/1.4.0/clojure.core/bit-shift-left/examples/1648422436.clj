;;a bogus bit-array implementation

(def ba (atom (long 0)))

(defn set-ba 
"sets bit n in long atom ba"
  [n]
  (let [number-set (bit-shift-left 1 n)
	_ (println "number to set: " number-set)
	new-array (bit-or @ba number-set)]
    (reset! ba new-array)))

(defn get-ba 
"gets bit n in long atom ba"
[n]
  (not (zero? (bit-and (bit-shift-left 1 n) @ba))))

(comment
  (set-ba 0) ;; 0 [....0001]
  (set-ba 3) ;; 2^3 = 8  [....1001]
  (get-ba 0) ;; (bit-and ba 2^0) = 1
  (get-ba 1) ;; (bit-and ba 2^1) = 0
  (get-ba 3) ;; (bit-and ba 2^3) = 1
  ;;but:
  (set-ba 65) ;; [....1011]
  ;;number to set:  2
  ;;modulo because long has only 64 bit
  ;;also note that long always is two-complemented (signed) in java implementation
  )