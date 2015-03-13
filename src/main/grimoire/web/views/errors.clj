(ns grimoire.web.views.errors
  (:require [clojure.java.io :as io]
            [grimoire.web.views :refer [site-config dispatch-fn header]]
            [grimoire.web.layout :refer [layout]]
            [grimoire.util :as util]
            [grimoire.things :as t]
            [ring.util.response :as response]))

;; 404 Error page
;; FIXME: these are all fucked
;; FIXME: all of these need to be type parametric

(defn error-404 []
  (layout
   (site-config)
   (slurp (io/resource "404.html"))))

(defn error-unknown-group [groupid]
  (let [groupid (t/thing->name groupid)]
    (layout
     (site-config)
     [:h1 {:class "page-title"}
      [:a {:href "/store/"} "Store"]]
     [:p "Unknown group " (pr-str (str groupid))]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo (site-config))}
       " github bugtracker."]])))

(defn error-unknown-artifact
  [artifact-thing]
  (let [group      (t/thing->group artifact-thing)
        artifactid (t/thing->name artifact-thing)]
    (layout
     (site-config)
     [:h1 {:class "page-title"}
      (header group)]
     [:p "Unknown artifact " (pr-str (str artifactid))]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo (site-config))}
       " github bugtracker."]])))

(defn error-unknown-version
  [version-thing]
  (let [groupid    (t/thing->name (t/thing->group version-thing))
        artifactid (t/thing->name (t/thing->artifact version-thing))
        version    (t/thing->name version-thing)]
    (layout
     (site-config)
     [:h1 {:class "page-title"}
      (header (:parent version-thing))]
     [:p "Unknown artifact version " (pr-str version)]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo (site-config))}
       " github bugtracker."]])))

(defn error-unknown-platform
  [platform-thing]
  (let [groupid    (t/thing->name (t/thing->group platform-thing))
        artifactid (t/thing->name (t/thing->artifact platform-thing))
        version    (t/thing->name (t/thing->version platform-thing))
        platformid (t/thing->name platform-thing)]
    (layout
     (site-config)
     [:h1 {:class "page-title"}
      (header (:parent platform-thing))]
     [:p "Unknown platform identifier " (pr-str (str platformid))]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo (site-config))}
       " github bugtracker."]])))

(defn error-unknown-namespace
  [ns-thing]
  (let [groupid    (t/thing->name (t/thing->group ns-thing))
        artifactid (t/thing->name (t/thing->artifact ns-thing))
        version    (t/thing->name (t/thing->version ns-thing))
        platform   (t/thing->name (t/thing->platform ns-thing))
        namespace  (t/thing->name ns-thing)]
    (layout
     (site-config)
     [:h1 {:class "page-title"}
      (header (:parent ns-thing))]
     [:p "Unknown namespace identifier " (pr-str namespace)]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo (site-config))}
       " github bugtracker."]])))

(defmulti error-unknown-symbol dispatch-fn
  :default :text/plain)

(defmulti search-no-symbol dispatch-fn
  :default :text/plain)

(defmulti search-no-version dispatch-fn
  :default :text/plain)

(load "errors/txt")
(load "errors/html")
