user=> (def x (to-array [32 9 11]))
#'user/x
user=> (seq x)
(32 9 11)
user=> (sort x)   ; returns sorted sequence
(9 11 32)
user=> (seq x)    ; but also modifies Java array x
(9 11 32)
user=> (sort (aclone x))   ; can avoid this by copying the array
(9 11 32)
;; Such copying is unnecessary for args that are not a Java array
