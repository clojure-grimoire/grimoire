;; redefine var
(def foo 1)
#'user/foo
(with-redefs [foo 2] foo)
2

;; redefine private var
(ns first)
(def ^:private foo 1)
#'first/foo

(ns second)
(with-redefs [first/foo 2] @#'first/foo)
2

;; @#' is the macros of (deref (var first/foo))
(with-redefs [first/foo 2] (deref (var first/foo))
2