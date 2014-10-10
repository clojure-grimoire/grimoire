;; Create a newline-terminated string from the items and store it in x.
user=> (def x (println-str 1 "foo" \b \a \r {:a 2}))
#'user/x

;; It's a string.
user=> (string? x)
true

;; Notice that the items are separated by a space.  Also, the newline string is
;; platform-specific. See clojure.core/newline.
user=> x
"1 foo b a r {:a 2}\r\n"
