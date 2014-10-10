;; rem and mod are commonly used to get the remainder.
;; mod means Gaussian mod, so the result is always
;; non-negative.  Don't confuse it with ANSI C's %
;; operator, which despite being although pronounced
;; 'mod' actually implements rem, i.e. -10 % 3 = -1.

user=> (mod -10 3)
2

user=> (rem -10 3)
-1