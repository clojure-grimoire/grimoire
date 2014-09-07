user=> (def phone-number "672-345-456-3212")
#'user/phone-number

user=> (def matcher (re-matcher #"((\d+)-(\d+))" phone-number))
#'user/matcher

user=> (re-find matcher)
["672-345" "672-345" "672" "345"]

;; re-groups gets the most recent find or matches
user=> (re-groups matcher)
["672-345" "672-345" "672" "345"]
user=> (re-groups matcher)
["672-345" "672-345" "672" "345"]


user=> (re-find matcher)
["456-3212" "456-3212" "456" "3212"]

user=> (re-groups matcher)
["456-3212" "456-3212" "456" "3212"]
user=> (re-groups matcher)
["456-3212" "456-3212" "456" "3212"]


user=> (re-find matcher)
nil

user=> (re-groups matcher)
IllegalStateException No match found  java.util.regex.Matcher.group (Matcher.java:468)