(ns grimoire.web.util
  (:require [clojure.java.io :as io]
	    [clojure.string :as string]
	    [markdown.core :as md]
	    [me.raynes.conch :refer [let-programs]])
  (:import (java.net URLEncoder)))

(defn cheatsheet
  "Slurps in the cheatsheet off of the resource path and does the final
  rendering to HTML."
  [{:keys [baseurl clojure-version]}]
  (-> "cheatsheet.html"
      io/resource
      slurp
      (string/replace #"\{\{ site.baseurl \}\}" "")))

(def cheatsheet-memo
  "Since the cheatsheet isn't expected to change and is the highest traffic page
  on the site just memoize it."
  (memoize cheatsheet))

(defn resource-file-contents
  "Slurps a file if it exists, otherwise returning nil."
  [file]
  (let [file (io/file file)]
    (when (.exists file)
      (some-> file slurp))))

(def header-regex
  #"^---\n((?:[a-z-]+: [^\n]+\n)*)---\n")

(defn parse-markdown-page-header
  "Attempts to locate a Jekyll style markdown header in a \"page\" given as a
  string and parse it out into a Clojure map. Returns a (possibly empty) map."
  [page]
  (when-let [header (some->> page
			     (re-find header-regex)
			     second)]
    (->> (string/split header #"\n")
	 (map #(string/split % #": "))
	 (map (juxt (comp keyword first) second))
	 (into {}))))

(def markdown-string
  md/md-to-html-string)

(defn parse-markdown-page
  "Attempts to slurp a markdown file from the resource path, returning a
  pair [header-map, html-string]."
  [page]
  (when-let [raw (some-> page (str ".md") io/resource slurp)]
    [(or (parse-markdown-page-header raw) {})
     (-> raw (string/replace header-regex "") markdown-string)]))

(def markdown-file
  "Helper for rendering a file on the resource path to HTML via markdown."
  (comp markdown-string resource-file-contents))

(defn highlight-clojure
  "Helper for rendering a string of Clojure to syntax highlighted HTML via
  pygmentize."
  [text]
  (let-programs [pygmentize "pygmentize"]
    (pygmentize "-fhtml"
                "-lclojure"
		"-Ostripnl=False,encoding=utf-8"
		{:in text})))

(defn url-encode
  "Returns an UTF-8 URL encoded version of the given string."
  [unencoded]
  (URLEncoder/encode unencoded "UTF-8"))

(defn moved-permanently
  "Returns a Ring response for a HTTP 301 'moved permanently' redirect."
  {:added "1.3"}
  ;; FIXME: remove for ring-clojure/ring#181
  [url]
  {:status  301
   :headers {"Location" url}
   :body    ""})

(def normalize-type
  {:html              :text/html
   :text/html         :text/html
   "html"             :text/html
   "text/html"        :text/html

   :text              :text/plain
   :text/plain        :text/plain
   "text"             :text/plain
   "text/plain"       :text/plain

   "json"             :application/json
   :json              :application/json
   "application/json" :application/json
   :application/json  :application/json

   "edn"              :application/edn
   :edn               :application/edn
   "application/edn"  :application/edn
   :application/edn   :application/edn
   })
