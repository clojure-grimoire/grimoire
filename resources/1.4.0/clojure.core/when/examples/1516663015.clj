user=> (def has-value (when true
                            (println "Hello World")
                            "Returned Value"))
Hello World
#'user/has-value

user=> has-value
"Returned Value"

