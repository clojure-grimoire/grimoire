(ns some.namespace
  (:require [clojure.contrib.json :as json])
  (:use [clojure.string :only [trim lower-case split]]
        [clojure.contrib.shell-out]
        [clojure.pprint]
        [clojure.test]))
