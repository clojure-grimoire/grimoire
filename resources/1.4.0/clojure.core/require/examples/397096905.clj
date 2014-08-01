(ns myproject.core
  (:use [clojure.core] :reload)
  (:require [clojure.string :as str :refer [replace]] :reload-all))

(str/replace "foo" #"o" "e")
"fee"