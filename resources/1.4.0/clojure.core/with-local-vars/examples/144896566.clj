;; with-local-vars allows you to write more imperative-style code, for cases
;; where you really want to.  factorial isn't a case where it helps, but
;; it is short and familiar.  Note that (var-get acc) can be abbreviated
;; as @acc
user=> (defn factorial [x]
         (with-local-vars [acc 1, cnt x]
           (while (> @cnt 0)
             (var-set acc (* @acc @cnt))
             (var-set cnt (dec @cnt)))
           @acc))
#'user/factorial
user=> (factorial 7)
5040
