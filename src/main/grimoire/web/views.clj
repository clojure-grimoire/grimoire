(ns grimoire.web.views
  (:require [detritus.variants :as var]
            [grimoire.util :as util]
            [grimoire.things :as t
             :refer [thing->path]]
            [grimoire.either
             :refer [succeed? result]]
            [grimoire.web.layout
             :refer [layout]]
            [grimoire.web.util :as wutil]
            [grimoire.api :as api]
            [grimoire.api.fs.read]
            [ring.util.response :as response]
            [sitemap.core :refer [generate-sitemap]]))

;; Site configuration
;;--------------------------------------------------------------------

(def site-config
  {:url                 "http://conj.io/"
   :repo                "https://github.com/clojure-grimoire/grimoire/"
   :baseurl             "/"
   :datastore           {:docs  "doc-store"
                         :notes "notes-store"
                         :mode  :filesystem}
   :version             (slurp "VERSION")
   :google-analytics-id "UA-44001831-2"
   :year                "2015"
   :author              {:me          "http://arrdem.com/"
                         :email       "me@arrdem.com"
                         :gittip      "https://gittip.com/arrdem/"}
   :style               {:header-sep  "/"
                         :title       "Grimoire - Community Clojure Documentation"
                         :description "Community documentation of Clojure"
                         :quote       "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."}})

;; Common partial pages
;;--------------------------------------------------------------------

(defn link-to [prefix x]
  {:href (str prefix (thing->path x))})

(def store-baseurl "/store/v0/")

(def link-to' (partial link-to store-baseurl))

(defn header [t]
  (cond
    (t/group? t)
    ,,(list [:a {:href store-baseurl}
             "store"] "/"
             [:a (link-to' t)
              ,,(t/thing->name t)])
        
    (t/artifact? t)
    ,,(list (header (t/thing->group t))
            "/" [:a (link-to' t)
                 ,,,(t/thing->name t)])

    (t/version? t)
    ,,(let [artifact (t/thing->artifact t)
            group    (t/thing->group artifact)]
        (list [:a {:href store-baseurl}
               "store"] "/"
               "[" [:a (link-to' group)
                    ,,(t/thing->name group)]
               "/" [:a (link-to' artifact)
                    ,,(t/thing->name artifact)]
               " " [:a (link-to' t)
                    ,,,(pr-str (t/thing->name t))] "]"))

    (t/platform? t)
    ,,(list (header (t/thing->version t)) " "
            [:a (link-to' t)
             ,,,(t/thing->name t)])

    (t/namespace? t)
    ,,(list (header (t/thing->platform t)) "::"
            [:a (link-to' t)
             ,,,(t/thing->name t)])

    (t/def? t)
    ,,(let [sym' (util/munge (t/thing->name t))]
        (list (header (t/thing->namespace t)) "/"
              [:a (link-to' t)
               ,,,(t/thing->name t)]))))

;; Pages
;;--------------------------------------------------------------------

;; FIXME: probably belongs somewhere else
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

(defmulti store-page identity)

(def dispatch-fn
  (fn [x & more]
    {:pre [(keyword? x)]}
    x))

(defmulti group-page dispatch-fn
  :default :text/plain)
;; FIXME: application/edn
;; FIXME: application/json

(defmulti artifact-page dispatch-fn
  :default :text/plain)
;; FIXME: application/edn
;; FIXME: application/json

(defmulti version-page dispatch-fn
  :default :text/plain)
;; FIXME: application/edn
;; FIXME: application/json

(defmulti platform-page dispatch-fn
  :default :text/plain)
;;FIXME: text/plain
;;FIXME: application/json
;;FIXME: application/edn

(defmulti namespace-page dispatch-fn
  :default :text/plain)
;; FIXME: application/edn
;; FIXME: application/json

;; FIXME: this should be a smarter cache
(def namespace-page-memo
  (memoize namespace-page))

(defmulti symbol-page dispatch-fn
  :default :text/plain)
;; FIXME: application/edn
;; FIXME: application/json

;; FIXME: How to deal with namespaces in different platforms?
;; FIXME: Probably belongs somewhere else
(def ns-version-index
  (->> (for [groupid   (result (api/list-groups     site-config))
             artifact  (result (api/list-artifacts  site-config groupid))
             :let      [version  (->> artifact
                                      (api/list-versions site-config)
                                      result first)
                        platform (->> version
                                      (api/list-platforms site-config)
                                      result (sort-by t/thing->name) first)]
             namespace (result (api/list-namespaces site-config platform))]
         [(t/thing->name namespace)
          version])
       (into {})))

(def -const-pages
  ["/"
   "/about"
   "/api"
   "/contributing"
   store-baseurl])

(def maybe
  (fn [x]
    (when (succeed? x)
      (result x))))

(def -sitemap-fn
  (memoize
   (fn []
     (->> (let [groups     ,,,,,,,,(maybe (api/list-groups site-config))
                artifacts  (mapcat (comp maybe (partial api/list-artifacts site-config)) groups)
                versions   (mapcat (comp maybe (partial api/list-versions site-config)) artifacts)
                platforms  (mapcat (comp maybe (partial api/list-platforms site-config)) versions)
                namespaces (mapcat (comp maybe (partial api/list-namespaces site-config)) platforms)
                defs       (mapcat (comp maybe (partial api/list-defs site-config)) namespaces)]
            (concat groups artifacts versions platforms namespaces defs))
          (map link-to')
          (concat -const-pages)
          (map (fn [x] {:loc x}))
          generate-sitemap))))

(defn sitemap-page []
  (-sitemap-fn))

;; Load view implementations
;;--------------------------------------------------------------------
(load "views/content/html")
(load "views/content/txt")
