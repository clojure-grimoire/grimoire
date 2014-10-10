(ns rosettacode.24game
  (:require [clojure.string :as str]))

(defn parse-infix-data
  "input '1+2+3+4'
   output (1 + 2 + 3 + 4)
   where the numbers are clojure numbers, and the symbols are clojure operators"
  [string] (map read-string (next (str/split string #""))))