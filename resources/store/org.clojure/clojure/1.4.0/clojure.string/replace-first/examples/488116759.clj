user=> (use '[clojure.string :only (replace-first)])

;; Pattern matches from beginning of string (signified by ^) up to the
;; last occurrence of /, because by default * is greedy, i.e. it
;; matches as much as possible.
user=> (replace-first "/path/to/file/name" #"^.*/" "")
"name"

;; Use *? to match as little as possible.
user=> (replace-first "/path/to/file/name" #"^.*?/" "")
"path/to/file/name"
