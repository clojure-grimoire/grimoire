(ns grimoire.web.util
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [grimoire
             [api :as api]
             [either :as e]
             [things :as t]]
            [grimoire.api.web :as web]
            [grimoire.web.config :as cfg]
            [markdown
             [core :as md]
             [transformers :as md.t]]
            [me.raynes.conch :refer [let-programs]]
            [pandect.algo.sha256 :refer [sha256]]
            [taoensso.timbre :refer [info]])
  (:import java.lang.ref.SoftReference
           java.net.URLEncoder))

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

(def maybe
  (fn [x]
    (when (e/succeed? x)
      (e/result x))))

(def mem-sts->t
  (memoize
   (fn [t]
     (let [res (api/resolve-short-string
                (cfg/lib-grim-config) t)]
       (when (e/fail? res)
         (println "Failed looking up" t))
       (maybe res)))))

(defn mem-sts->link
  [s]
  {:pre [(string? s)]}
  (when-let [t (mem-sts->t s)]
    [:a {:href (web/make-html-url (cfg/web-config) t)} s]))

(defn mem-sts->md-link
  [s]
  (if-let [t (-> s mem-sts->t)]
    (format "[%s](%s)"
            (-> s
                (string/replace "*" "\\*")
                (string/replace "_" "\\_"))
            (web/make-html-url (cfg/web-config) t))
    (if (.endsWith s ".")
      (str (mem-sts->md-link (.substring s 0 (dec (count s)))) ".")
      s)))

(declare highlight-text)

(defn markdown-highlight [text state]
  (let [text (string/join text)]
    [(string/replace text
                     #"(?si)```(\w*)\n\r?(.*?)```"
                     (fn [[_ lang code :as match]]
                       (highlight-text lang code)))
     state]))

(defn thing-string->link [s]
  (-> (get s 0)
      (mem-sts->md-link)))

(defn markdown-string [m]
  (-> m
      (string/replace t/short-string-pattern thing-string->link)
      (md/md-to-html-string :replacement-transformers
                            (list* markdown-highlight
                                   md.t/transformer-vector))))

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

(defn highlight-text
  "Helper for rendering a string of program source to syntax
  highlighted HTML via pygmentize."
  [lang text]
  (let-programs [pygmentize "pygmentize"]
                (pygmentize "-fhtml"
                            (str "-l" lang)
                            "-Ostripnl=False,encoding=utf-8"
                            {:in text})))

(def highlight-clojure
  (partial highlight-text "clojure"))

(defmacro html-cache-thing
  {:style/indent 1}
  [t & body]
  `(let [uri#        (t/thing->full-uri ~t)
         cache-dir#  (io/file "render-cache")
         cache-file# (io/file cache-dir# (str (sha256 uri#) ".html"))]
     (if-not (.exists cache-dir#)
       (.mkdirs cache-dir#))

     (if (and (.exists cache-file#)
              (-> (cfg/site-config) :dev not))
       (slurp cache-file#)

       (do (info (str "Cache miss on thing " uri#))
           (let [text# (do ~@body)]
             (spit cache-file# text#)
             text#)))))

(defn highlight-example
  "Helper for rendering a Grimoire Example Thing to HTML, using a filesystem
  cache of rendered examples to improve performance."
  [ex]
  {:pre [(t/example? ex)]}
  (html-cache-thing ex
    (let [text (e/result (api/read-example (cfg/lib-grim-config) ex))
          html (highlight-clojure text)]
      html)))

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

(defn moved-temporary
  "Returns a Ring response for a HTTP 307 'moved temporarily' redirect."
  [url]
  {:status  307
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

(defmacro softref-cached [& body]
  `(let [cache# (atom (SoftReference. nil))]
     (fn []
       (or (.get ^SoftReference @cache#)
           (let [res# (do ~@body)]
             (reset! cache# (SoftReference. res#))
             res#)))))
