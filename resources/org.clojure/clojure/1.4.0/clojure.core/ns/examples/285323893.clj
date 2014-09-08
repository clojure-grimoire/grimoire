;; In clojure 1.4 and higher you can use the refer function from within
;; a require which is equivalent to (:use foo only [...]) but still 
;; allows you to reference the required namespace:
(ns my.ns.example
    (:require [my.lib :refer [function1 function2]]))

;; And :refer :all is equivalent to :use :
(ns my.ns.example
    (:require [my.lib :refer :all]))
