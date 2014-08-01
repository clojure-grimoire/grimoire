user=> (def *strings* ["str1" "str2" "str3"])
#'user/*strings*

;; Oops!
user=> (str *strings*)
"[\"str1\" \"str2\" \"str3\"]"

;; Yay!
user=> (apply str *strings*)
"str1str2str3"
user=>

;; Note the equivalence of the following two forms
user=> (apply str ["str1" "str2" "str3"])
"str1str2str3"

user=> (str "str1" "str2" "str3")
"str1str2str3"
