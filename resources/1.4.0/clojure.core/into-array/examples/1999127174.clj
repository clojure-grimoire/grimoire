;; java.nio.file.Paths#get takes (String, String...)
user=> (java.nio.file.Paths/get "/Users" (into-array ["username" "dev" "clojure"]))
#<UnixPath /Users/username/dev/clojure>