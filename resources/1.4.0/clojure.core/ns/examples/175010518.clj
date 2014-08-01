(ns rosettacode.24game
  (:require [clojure.string :as str])
  (:use clojure.test))

(deftest test
 (is (= "ABC" (str/capitalize "abc")))