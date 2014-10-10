;; only functions can be used with apply.  'and' is a macro
;; because it needs to evaluate its arguments lazily and so
;; does not work with apply.
user=> (apply and (list true true false true)

-> ERROR

