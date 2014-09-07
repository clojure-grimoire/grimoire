;; re-find can be used to iterate through re matches in the string

user=> (def phone-number "672-345-456-3212")
#'user/phone-number

user=> (def matcher (re-matcher #"\d+" phone-number))
#'user/matcher

user=> (re-find matcher)
"672"

user=> (re-find matcher)
"345"

user=> (re-find matcher)
"456"

user=> (re-find matcher)
"3212"

;; when there's no more valid matches, nil is returned
user=> (re-find matcher)
nil