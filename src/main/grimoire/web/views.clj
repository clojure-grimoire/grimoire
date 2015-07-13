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
            [grimoire.api.fs.read]
            [grimoire.api.web :as web]
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

(defmulti store-page identity)

(def dispatch-fn
  (fn [x & more]
    {:pre [(keyword? x)]}
    x))

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
(def ns-version-index
  (memoize
   (fn []
     (->> (for [groupid   (result (api/list-groups     (lib-grim-config)))
                artifact  (result (api/list-artifacts  (lib-grim-config) groupid))
                :let      [version  (->> artifact
                                         (api/list-versions (lib-grim-config))
                                         result first)
                           platform (->> version
                                         (api/list-platforms (lib-grim-config))
                                         result (sort-by t/thing->name) first)]
                namespace (result (api/list-namespaces (lib-grim-config) platform))]
            [(t/thing->name namespace)
             version])
          (into {})))))

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
                groups     ,,,,,,,,(maybe (api/list-groups cfg))
                artifacts  (mapcat (comp maybe (partial api/list-artifacts cfg)) groups)
                versions   (mapcat (comp maybe (partial api/list-versions cfg)) artifacts)
                platforms  (mapcat (comp maybe (partial api/list-platforms cfg)) versions)
                namespaces (mapcat (comp maybe (partial api/list-namespaces cfg)) platforms)
                defs       (mapcat (comp maybe (partial api/list-defs cfg)) namespaces)]
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
