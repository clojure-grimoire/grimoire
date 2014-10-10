(defn is-small? [number]
  (if (< number 100) "yes" "no"))

user=> (is-small? 50)
"yes"

user=> (is-small? 500)
"no"