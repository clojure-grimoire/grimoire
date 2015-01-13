(ns grimoire.web.util
  (:require [clojure.java.io :as io]
	    [clojure.string :as string]
	    [markdown.core :as md]
	    [me.raynes.conch :refer [let-programs]])
  (:import (java.net URLEncoder)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Cheatsheet

(defn cheatsheet [{:keys [baseurl clojure-version]}]
  (-> "cheatsheet.html"
      io/resource
      slurp
      (string/replace #"\{\{ site.baseurl \}\}" "")))

(def cheatsheet-memo (memoize cheatsheet))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Jekyll-based Markdown pages

(def header-regex #"^---\n((?:[a-z-]+: [^\n]+\n)*)---\n")

(defn parse-markdown-page-header [page]
  (when-let [header (some->> page
			     (re-find header-regex)
			     second)]
    (->> (string/split header #"\n")
	 (map #(string/split % #": "))
	 (map (juxt (comp keyword first) second))
	 (into {}))))

(defn parse-markdown-page [page]
  (when-let [raw (some-> page (str ".md") io/resource slurp)]
    [(or (parse-markdown-page-header raw) {})
     (-> raw (string/replace header-regex "") md/md-to-html-string)]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Documentation data file contents

(defn resource-file-contents [file]
  (let [file (io/file file)]
    (when (.exists file)
      (some-> file slurp))))

(def markdown-string md/md-to-html-string)

(defn markdown-file [file]
  (-> file resource-file-contents markdown-string))

(defn highlight-clojure [text]
  (let-programs [pygmentize "pygmentize"]
    (pygmentize "-fhtml"
                "-lclojure"
		"-Ostripnl=False,encoding=utf-8"
		{:in text})))

(defn clojure-file [file]
  (some-> file resource-file-contents highlight-clojure))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Documentation data file lists

(defn dir-list-as-strings [& path-elements]
  (let [dir (apply io/file path-elements)]
    (when (.exists dir)
      (->> (.listFiles dir)
	   (map str)))))

(defn prepare-path [path]
  (-> path
      (string/replace #"resources/" "")
      (string/split #"/")))

(defn paths [& path-elements]
  (->> path-elements
       (concat ["resources"])
       (apply dir-list-as-strings)
       (map prepare-path)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; URL encoding

(defn url-encode
  "Returns an UTF-8 URL encoded version of the given string."
  [unencoded]
  (URLEncoder/encode unencoded "UTF-8"))

(def clojure-versions
  ["1.6.0" "1.5.0" "1.4.0"])
