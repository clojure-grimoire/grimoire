(ns grimoire.site.release
  (:require [compojure.core :refer :all]
            [hiccup.page :as page]
            [clojure.java.io :as io]))

(defn header
  [page]
  (-> (slurp (io/file "_include/header.html"))
      (mustache/render page)))

(defn sidebar
  [page]
  (-> (slurp (io/file "_include/sidebar.html"))
      (mustache/render page)))

(defn masthead
  [page]
  (-> (slurp (io/file "_include/masthead.html"))
      (mustache/render page)))

(defn foot
  [page]
  (-> (slurp (io/file "_include/foot.html"))
      (mustache/render page)))

(defn default
  [page content]
  (page/html5
   (header page)
   [:body
    (sidebar page)
    [:wrap (masthead page)
     [:div {:class "container content"}
      content]]]
   (foot page)))

;;--------------------------------------------------------------------

(defn page-layout
  [env & content]
  (default env
    [:div {:class "page"}
     [:h1 {:class "page-title"}
      (-> env :page :title)]
     content]))

(defn release-layout
  [env & content]
  (-> (update-in env [:page :title] #(str "Clojure " %))
      (apply page-layout content)))

(defn ns-layout
  [env & content]
  (default page
    [:div {:class "page"}
     [:h1 {:class "page-title"}
      [:a {:href "../"}
       (->> env :page :release)]
      (->> env :page :title)]
     content]
    [:script {:type "text/javascript" :src "/public/jquery.js"}]
    [:script {:type "text/javascript" :src "/public/namespace.js"}]))

(defn fn-layout
  [env & content]
  (apply page-layout env content))

;;--------------------------------------------------------------------

(def site
  {:site {}})

;;--------------------------------------------------------------------

(defn render-version
  [version]
  )

(defn render-namespace
  [version namespace]
  )

(defn render-symbol
  [version namespace symbol]
  )

;;--------------------------------------------------------------------

(defroutes routes
  (GET "/:version/"
       [version]
       (render-version version))

  (GET "/:version/:namespace/"
       [version namespace]
       (render-namespace [version namespace]))

  (GET "/:version/:namespace/:symbol/"
       {{:keys [version namespace symbol]} :params
        {type :type} :headers}
       (render-symbol version namespace symbol type)))
