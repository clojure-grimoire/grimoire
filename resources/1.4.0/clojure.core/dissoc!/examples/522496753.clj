;; dissoc! works on a transient map

;; WARNING: Below is an example of what is called "bashing in place" of
;; a transient, and is _NOT_ the correct way to use transients.  See assoc!
;; examples for some discussion of the reason.

(let [my-map (transient {:x 1 :y 2 :z 3})]
  (dissoc! my-map :x)   ; mistake is to use my-map below, not dissoc! return val
  (persistent! my-map)) ; returns persistent map {:y 2 :z 3}


;; Here is a correct way to do the operation described above:

(let [my-map (transient {:x 1 :y 2 :z 3})
      x (dissoc! my-map :x)]    ; after this, don't use my-map again, only x
  (persistent! x))    ; returns persistent map {:y 2 :z 3}