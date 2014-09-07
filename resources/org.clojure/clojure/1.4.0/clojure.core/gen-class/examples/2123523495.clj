;; I found managing state a bit confusing at first.
;; here's a dumb little class with a getter and setter for a "location" field.

(ns com.example )

(gen-class
      :name com.example.Demo
      :state state
      :init init
      :prefix "-"
      :main false
      ;; declare only new methods, not superclass methods
      :methods [[setLocation [String] void]
                [getLocation [] String]])

;; when we are created we can set defaults if we want.
(defn -init []
  "store our fields as a hash"
  [[] (atom {:location "default"})])

;; little functions to safely set the fields.
(defn setfield
  [this key value]
      (swap! (.state this) into {key value}))

(defn getfield
  [this key]
  (@(.state this) key))

;; "this" is just a parameter, not a keyword
(defn -setLocation [this loc]
  (setfield this :location loc))

(defn  -getLocation
  [this]
  (getfield this :location))

;; running it -- you must compile and put output on the classpath
;; create a Demo, check the default value, then set it and check again.
user=> (def ex (com.example.Demo.))
#'user/ex
user=> (.getLocation ex)
"default"
user=> (.setLocation ex "time")
nil
user=> (.getLocation ex)
"time"
