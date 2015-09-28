(ns grimoire.web.views
  (:require [grimoire.util :as util]
            [grimoire.things :as t
             :refer [thing->path]]
            [grimoire.either
             :refer [succeed? result]]
            [grimoire.web.layout
             :refer [layout]]
            [grimoire.web.util :as wutil
             :refer [maybe]]
            [grimoire.web.config
             :refer [lib-grim-config
                     site-config
                     web-config]]
            [grimoire.api :as api]
            [grimoire.api.web :as web]
            [grimoire.api.fs.read]
            [grimoire.web.caches :as c]
            [ring.util.response :as response]
            [sitemap.core
             :refer [generate-sitemap]]))

;; Common partial pages
;;--------------------------------------------------------------------

(defn link-to [x]
  {:href (web/make-html-url (web-config) x)})

(defn header [t]
  (cond
    (t/group? t)
    ,,(list [:a {:href (-> (site-config) :store-url)}
             "store"] "/"
             [:a (link-to t)
              ,,(t/thing->name t)])

    (t/artifact? t)
    ,,(list (header (t/thing->group t))
            "/" [:a (link-to t)
                 ,,,(t/thing->name t)])

    (t/version? t)
    ,,(let [artifact (t/thing->artifact t)
            group    (t/thing->group artifact)]
        (list [:a {:href (-> (site-config) :store-url)}
               "store"] "/"
               "[" [:a (link-to group)
                    ,,(t/thing->name group)]
               "/" [:a (link-to artifact)
                    ,,(t/thing->name artifact)]
               " " [:a (link-to t)
                    ,,,(pr-str (t/thing->name t))] "]"))

    (t/platform? t)
    ,,(list (header (t/thing->version t)) " "
            [:a (link-to t)
             ,,,(t/thing->name t)])

    (t/namespace? t)
    ,,(list (header (t/thing->platform t)) "::"
            [:a (link-to t)
             ,,,(t/thing->name t)])

    (t/def? t)
    ,,(let [sym' (util/munge (t/thing->name t))]
        (list (header (t/thing->namespace t)) "/"
              [:a (link-to t)
               ,,,(t/thing->name t)]))))

;; Pages
;;--------------------------------------------------------------------

;; FIXME: probably belongs somewhere else
(defn markdown-page
  "Helper for rendering a markdown page off of the resource path as HTML"
  [page]
  (let [[header page] (wutil/parse-markdown-page page)
        title         (get header :title (clojure.string/capitalize page))]
    (layout
     (update-in (site-config)
                [:style :title] #(str title " - " %))
     (if page
       (list [:h1 {:class "page-title"} title]
             page)
       (response/not-found
        "Resource not found, sorry. Please file an issue on the github bugtracker.")))))

(def dispatch-fn
  (fn [x & more]
    (cond (keyword? x) x
          (map? x)     (:content-type x)
          :else        nil)))

(defmulti store-page dispatch-fn
  :default :text/html)

(defmulti group-page dispatch-fn
  :default :text/plain)

(defmulti artifact-page dispatch-fn
  :default :text/plain)

(defmulti version-page dispatch-fn
  :default :text/plain)

(defmulti platform-page dispatch-fn
  :default :text/plain)

(defmulti namespace-page dispatch-fn
  :default :text/plain)

(defmulti symbol-page dispatch-fn
  :default :text/plain)

;; FIXME: How to deal with namespaces in different platforms?
;; FIXME: Probably belongs somewhere else
(defn -ns-version-index []
  (let [*cfg* (lib-grim-config)
        γ     (fn [f coll] (maybe (f *cfg* coll)))]
    (->> (for [groupid   (result (api/list-groups *cfg*))
               artifact  (γ api/list-artifacts groupid)
               :let      [version  (first (γ api/list-versions artifact))
                          platform (->> (γ api/list-platforms version)
                                        (sort-by t/thing->name)
                                        first)]
               namespace (γ api/list-namespaces platform)]
           [(t/thing->name namespace) version])
         (into {}))))

(def ns-version-index
  (memoize -ns-version-index))

(def -const-pages
  ["/"
   "/about"
   "/api"
   "/contributing"
   (-> (site-config) :store-url)])

(def -sitemap-fn
  (memoize
   (fn []
     (->> (let [cfg        (lib-grim-config)
                φ          (fn [f g] (mapcat (comp maybe (partial f cfg)) g))
                groups     (-> cfg api/list-groups maybe)
                artifacts  (φ api/list-artifacts groups)
                versions   (φ api/list-versions artifacts)
                platforms  (φ api/list-platforms versions)
                namespaces (φ api/list-namespaces platforms)
                defs       (φ api/list-defs namespaces)]
            (concat groups artifacts versions platforms namespaces defs))
          (map link-to)
          (concat -const-pages)
          (map (fn [x] {:loc x}))
          generate-sitemap))))

(defn sitemap-page []
  (-sitemap-fn))

;; Load view implementations
;;--------------------------------------------------------------------
(load "views/content/html")
(load "views/content/txt")
