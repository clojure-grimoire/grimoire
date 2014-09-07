;; To title case
(clojure.string/replace "hello world" #"\b." #(.toUpperCase %1))
"Hello World"
;; Note that a vector is passed to your replacement function
;; when pattern contains capturing groups (see re-groups)
(clojure.string/replace "hello world" #"\b(.)" #(.toUpperCase (%1 1)))
"Hello World"
