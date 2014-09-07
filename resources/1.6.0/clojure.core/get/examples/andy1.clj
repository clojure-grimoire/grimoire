user=> (get #{"a" 'b 5 nil} 'b)  ; symbol b is in the set
b
user=> (get #{"a" 'b 5 nil} 2)   ; 2 is not
nil
user=> (get #{"a" 'b 5 nil} nil) ; nil is in this set, but one
nil                              ; cannot distinguish this result from not being in the set.
user=> (get #{"a" 'b 5} nil)     ; Result is same here.
nil

;; You may specify a not-found value to help distinguish these cases.
;; This works well as long as there is some value you know is not in the
;; set.

user=> (get #{"a" 'b 5} nil :not-found)
:not-found
user=> (get #{"a" 'b 5 nil} nil :not-found)
nil

;; Similarly for maps:

user=> (get {"a" 1, "b" nil} "b")
nil            ; found key "b", but value is nil
user=> (get {"a" 1, "b" nil} "b" :not-found)
nil            ; here we can tell it was found and value is nil
user=> (get {"a" 1, "b" nil} "c" :not-found)
:not-found     ; but here no key "c" was found
