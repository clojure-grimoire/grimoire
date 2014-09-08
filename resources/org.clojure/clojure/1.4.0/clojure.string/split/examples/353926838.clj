(use '[clojure.string :only (split)])

;; Split on every occurrence of : character
user=> (split "root:*:0:0:admin:/var/root:/bin/sh" #":")
["root" "*" "0" "0" "admin" "/var/root" "/bin/sh"]

;; Empty strings are returned when two colons appear consecutively in
;; the string to be split.
user=> (split "root::0:0::/var/root:/bin/sh" #":")
["root" "" "0" "0" "" "/var/root" "/bin/sh"]

;; Without specifying a limit, any empty strings at the end are
;; omitted.
user=> (split "root::0:0:admin:/var/root:" #":")
["root" "" "0" "0" "admin" "/var/root"]
user=> (split "root::0:0:admin::" #":")
["root" "" "0" "0" "admin"]

;; If you want all of the fields, even trailing empty ones, use a
;; negative limit.
user=> (split "root::0:0:admin:/var/root:" #":" -1)
["root" "" "0" "0" "admin" "/var/root" ""]
user=> (split "root::0:0:admin::" #":" -1)
["root" "" "0" "0" "admin" "" ""]

;; Use a positive limit of n to limit the maximum number of strings in
;; the return value to n.  If it returns exactly n strings, the last
;; contains everything left over after splitting off the n-1 earlier
;; strings.
user=> (split "root::0:0:admin:/var/root:" #":" 2)
["root" ":0:0:admin:/var/root:"]
user=> (split "root::0:0:admin:/var/root:" #":" 3)
["root" "" "0:0:admin:/var/root:"]
user=> (split "root::0:0:admin:/var/root:" #":" 4)
["root" "" "0" "0:admin:/var/root:"]
user=> (split "root::0:0:admin:/var/root:" #":" 15)
["root" "" "0" "0" "admin" "/var/root" ""]
