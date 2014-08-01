(ns foo.bar
  (:refer-clojure :exclude [ancestors printf])
  (:require [clojure.contrib sql sql.tests])
  (:use [my.lib this that])
  (:import [java.util Date Timer Random]
    (java.sql Connection Statement)))