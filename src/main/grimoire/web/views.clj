(ns grimoire.web.views
  (:require [grimoire.util :as util
             :refer [succeed? result]]
            [grimoire.web.layout
             :refer [layout]]
            [grimoire.web.util :as wutil]
            [grimoire.api :as api]
            [ring.util.response :as response]))

;; Boot the grimoire fs backend. Wish I could put this somewhere else,
;; but this is kinda where it fell.
(load "/grimoire/api/fs")
(load "/grimoire/api/fs/read")

(def site-config
  {:url                 "http://conj.io/"
   :repo                "https://github.com/clojure-grimoire/grimoire/"
   :baseurl             "/"
   :datastore           {:docs  "doc-store"
                         :notes "notes-store"
                         :mode  :filesystem}
   :version             (slurp "VERSION")
   :google-analytics-id "UA-44001831-2"
   :year                "2014"
   :author              {:me          "http://arrdem.com/"
                         :email       "me@arrdem.com"
                         :gittip      "https://gittip.com/arrdem/"}
   :style               {:header-sep  "/"
                         :title       "Grimoire - Community Clojure Documentation"
                         :description "Community documentation of Clojure"
                         :quote       "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."}})

(defn markdown-page
  "Helper for rendering a markdown page off of the resource path as HTML"
  [page]
  (let [[header page] (wutil/parse-markdown-page page)]
    (layout
     site-config
     (if page
       (list (when-let [title (:title header)]
               [:h1 title])
             page)
       (response/not-found "Resource not found, sorry. Please file an issue on the github bugtracker.")))))

(defn link-to [prefix x]
  {:href (str prefix (:uri x))})

(def link-to' (partial link-to "/store/"))

(defmulti header :type)

(defmethod header :group [group]
  (list [:a {:href "/store/"}
         "store"] "/"
         [:a (link-to' group)
          ,,(:name group)]))

(defmethod header :artifact [artifact]
  (list (header (:parent artifact))
        "/" [:a (link-to' artifact)
             ,,,(:name artifact)]))

(defmethod header :version [version]
  (list "[" (header (:parent version))
        " " [:a (link-to' version)
             ,,,(pr-str (:name version))] "]"))

(defmethod header :namespace [namespace]
  (list (header (:parent namespace)) " "
        [:a (link-to' namespace)
         ,,,(:name namespace)]))

(defmethod header :def [symbol]
  (let [sym'   (util/munge (:name symbol))]
    (list (header (:parent symbol)) "/"
          [:a (link-to' symbol)
           ,,,(:name symbol)])))

;; Pages
;;--------------------------------------------------------------------

(defn home-page []
  (layout
   site-config
   [:blockquote [:p (-> site-config :style :quote)]]
   (wutil/cheatsheet-memo site-config)))

(defmulti store-page identity)

(def dispatch-fn
  (fn [x y]
    {:pre [(and x y)
           (keyword? x)]}
    x))

(defmulti group-page dispatch-fn)
;; FIXME: text/zalgo
;; FIXME: application/edn
;; FIXME: application/json

(defmulti artifact-page dispatch-fn)
;; FIXME: text/zalgo
;; FIXME: text/plain
;; FIXME: application/edn
;; FIXME: application/json

(defmulti version-page dispatch-fn)
;; FIXME: text/zalgo
;; FIXME: text/plain
;; FIXME: application/edn
;; FIXME: application/json

(defmulti namespace-page dispatch-fn)
;; FIXME: text/zalgo
;; FIXME: text/plain
;; FIXME: application/edn
;; FIXME: application/json

;; FIXME: this should be a smarter cache
(def namespace-page-memo
  (memoize namespace-page))

(defmulti symbol-page dispatch-fn)
;; FIXME: text/zalgo
;; FIXME: application/edn
;; FIXME: application/json

(def ns-version-index
  (->> (for [groupid                   (result (api/list-groups     site-config))
           artifact                  (result (api/list-artifacts  site-config groupid))
           :let      [version (first (result (api/list-versions   site-config artifact)))]
           namespace                 (result (api/list-namespaces site-config version))]
       [(:name namespace) version])
     (into {})))

;; FIXME: code loading is evil

(load "views/content/html")
(load "views/content/txt")
