;; from http://groups.google.com/group/clojure/msg/71702435ccd1d189
user> (import java.util.Date)
java.util.Date

user> (def d (proxy [Date] [] (toString [] "hello")))
#'user/d

user> d
#<Date$0 hello>

user> (.toString d)
"hello"

user> (.toGMTString d)
"17 Nov 2010 12:57:28 GMT"

user> (update-proxy d {"toGMTString" (fn [this] "goodbye")})
nil

user> (.toGMTString d)
"goodbye" 