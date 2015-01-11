(ns grimoire.web.views.errors
  (:require [clojure.java.io :as io]
            [grimoire.web.views :refer [site-config dispatch-fn]]
            [grimoire.web.layout :refer [layout]]
            [grimoire.util :as util]
            [grimoire.things :as t]
            [ring.util.response :as response]))

;; 404 Error page
;; FIXME: these are all fucked
;; FIXME: all of these need to be type parametric

(defn error-404 []
  (layout
   site-config
   (slurp (io/resource "404.html"))))

(defn error-unknown-group [groupid]
  (let [groupid (:name groupid)]
    (layout
     site-config
     [:h1 {:class "page-title"}
      [:a groupid]]
     [:p "Unknown group " (pr-str groupid)]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo site-config)}
       " github bugtracker."]])))

(defn error-unknown-artifact
  [artifact-thing]
  (let [groupid    (:name (t/thing->group artifact-thing))
        artifactid (:name artifact-thing)]
    (let [s (format "%s/%s" groupid artifactid)]
      (layout
       site-config
       [:h1 {:class "page-title"}
        [:a s]]
       [:p "Unknown artifact " s]
       [:p "If you found a broken link, please report the issue encountered on the "
        [:a {:href (:repo site-config)}
         " github bugtracker."]]))))

(defn error-unknown-version
  [version-thing]
  (let [groupid    (:name (t/thing->group version-thing))
        artifactid (:name (t/thing->artifact version-thing))
        version    (:name version-thing)]
    (layout
     site-config
     [:h1 {:class "page-title"}
      [:a (format "[%s/%s \"%s\"]" groupid artifactid version)]]
     [:p "Unknown artifact version " (pr-str version)]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo site-config)}
       " github bugtracker."]])))

(defn error-unknown-platform
  [ns-thing]
  (let [groupid    (:name (t/thing->group ns-thing))
        artifactid (:name (t/thing->artifact ns-thing))
        version    (:name (t/thing->version ns-thing))
        platformid (:name ns-thing)]
    (layout
     site-config
     [:h1 {:class "page-title"}
      [:a (format "[%s/%s \"%s\"] %s" groupid artifactid version platformid)]]
     [:p "Unknown namespace identifier " platformid ":" (pr-str [version namespace])]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo site-config)}
       " github bugtracker."]])))

(defn error-unknown-namespace
  [ns-thing]
  (let [groupid    (:name (t/thing->group ns-thing))
        artifactid (:name (t/thing->artifact ns-thing))
        version    (:name (t/thing->version ns-thing))
        platform   (:name (t/thing->platform ns-thing))
        namespace  (:name ns-thing)]
    (layout
     site-config
     [:h1 {:class "page-title"}
      [:a (format "[%s/%s \"%s\"] %s" groupid artifactid version platform)]]
     [:p "Unknown namespace identifier " (pr-str [version namespace])]
     [:p "If you found a broken link, please report the issue encountered on the "
      [:a {:href (:repo site-config)}
       " github bugtracker."]])))

(defmulti error-unknown-symbol dispatch-fn
  :default :text/plain)

(defmulti search-no-symbol dispatch-fn
  :default :text/plain)

(defmulti search-no-version dispatch-fn
  :default :text/plain)

(load "errors/txt")
(load "errors/html")
