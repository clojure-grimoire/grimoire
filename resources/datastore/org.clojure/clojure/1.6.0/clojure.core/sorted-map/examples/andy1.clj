user=> (def births
         (sorted-map -428 "Plato"      -384 "Aristotle" -469 "Socrates"
                     -320 "Euclid"     -310 "Aristarchus" 90 "Ptolemy"
                     -570 "Pythagoras" -624 "Thales"    -410 "Eudoxus"))
#'user/births
user=> (first births)
[-624 "Thales"]
user=> (take 4 births)
([-624 "Thales"] [-570 "Pythagoras"] [-469 "Socrates"] [-428 "Plato"])
user=> (keys births)
(-624 -570 -469 -428 -410 -384 -320 -310 90)
user=> (vals births)   ; returns values in order by sorted keys
("Thales" "Pythagoras" "Socrates" "Plato" "Eudoxus" "Aristotle" "Euclid" "Aristarchus" "Ptolemy")

;; subseq and rsubseq return a sequence of all key/value pairs with a
;; specified range of keys.  It takes O(log N) to find the first pair,
;; where N is the size of the whole map, and O(1) time for each
;; additional pair, so it is more efficient than the O(N) approach of
;; taking the entire sequence and filtering out the unwanted pairs.

user=> (subseq births > -400)
([-384 "Aristotle"] [-320 "Euclid"] [-310 "Aristarchus"] [90 "Ptolemy"])
user=> (subseq births > -400 < -100)
([-384 "Aristotle"] [-320 "Euclid"] [-310 "Aristarchus"])
user=> (rsubseq births > -400 < -100)
([-310 "Aristarchus"] [-320 "Euclid"] [-384 "Aristotle"])
