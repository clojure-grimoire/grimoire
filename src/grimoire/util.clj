(ns grimoire.util
  (:refer-clojure :exclude [replace])
  (:require [clojure.string :refer [replace replace-first]]))

(defn var->name [v]
  {:pre [(var? v)]}
  (-> v .sym str))

(defn var->ns [v]
  {:pre [(var? v)]}
  (-> v .ns ns-name str))

(defn macro? [v]
  {:pre [(var? v)]}
  (:macro (meta v)))

(defn trim-dot
  [s]
  (replace-first s "./" ""))

(def prior-clojure-version
  {"1.4.0" "1.3.0"
   "1.5.0" "1.4.0",
   "1.6.0" "1.5.0"})

(defn my-munge [s]
  (-> s
      (replace "*" "_STAR_")
      (replace "?" "_QMARK_")
      (replace "." "_DOT_")
      (replace "<" "_LT_")
      (replace ">" "_GT_")
      (replace "-" "_DASH_")
      (replace "/" "_SLASH_")
      (replace "!" "_BANG_")
      (replace "=" "_EQ_")
      (replace "+" "_PLUS_")
      (replace "'" "_SQUOTE_")
      (replace #"^_*" "")
      (replace #"_*$" "")))

(defn var->link
  [v]
  {:pre  [(var? v)]
   :post [(string? %)]}
  (format "[%s](%s)\n"
          (replace (var->name v) "*" "\\*")
          (str "./" (-> v var->name my-munge) "/")))

(defn sym->link
  [s]
  {:pre  [(symbol? s)]
   :post [(string? %)]}
  (format "[%s](%s)\n"
          (replace (name s) "*" "\\*")
          (str "./" (-> s name my-munge) "/")))
