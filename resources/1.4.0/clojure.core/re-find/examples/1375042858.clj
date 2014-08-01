user=> (def *matcher* (re-matcher #"\d+" "abc12345def"))
#'user/*matcher*

user=> (re-find *matcher*)
"12345"

;; If you only want the first match, it is shorter to call re-find with the
;; pattern and the string to search, rather than explicitly creating a matcher
;; as above.
user=> (re-find #"\d+" "abc12345def")
"12345"

;; If you want all matches as a sequence, use re-seq.  Creating a matcher
;; explicitly with re-matcher and passing it to re-find is only the best way
;; if you want to write a loop that iterates through all matches, and do not
;; want to use re-seq for some reason.
