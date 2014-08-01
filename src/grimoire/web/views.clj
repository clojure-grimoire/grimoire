(ns grimoire.web.views
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [compojure.core :refer [GET]]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as util]
            [ring.util.response :as response]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Site config

(def site-config
  {:base-title          "Grimoire - Clojure Documentation"
   :description         "Community documentation of Clojure"
   :url                 "http://grimoire.arrdem.com"
   :baseurl             "/"
   :version             "0.3.0"
   :clojure-version     "1.6.0"
   :google-analytics-id "UA-44001831-2"
   :year                "2014"
   :author              {:me         "http://arrdem.com/"
                         :email      "me@arrdem.com"
                         :github     "https://github.com/arrdem/grimoire"}
   :style               {:header-sep " &raquo; "}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 404 Error page

(defn error-404 []
  (layout
   site-config
   (slurp (io/resource "404.html"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Generic markdown page

(defn markdown-page [page]
  (let [[header page] (util/parse-markdown-page page)
        site-config (merge site-config header)]
    (layout
     site-config
     (if page
       (list (when-let [title (:title site-config)]
               [:h1 title])
             page)
       (response/not-found "Not found, sorry.")))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Home page

(def quote
  "Even the most powerful wizard must consult grimoires as an aid against forgetfulness.")

(defn home-page []
  (layout
   site-config
   [:blockquote [:p quote]]
   (util/cheatsheet-memo site-config)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Version page

(defn version-page [version]
  (layout
   site-config
   (util/markdown-file (str "resources/" version "/index.md"))
   [:h3 [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]]
   [:h2 "Namespaces"]
   [:ul
    (for [path (util/paths version)]
      [:li [:a {:href (str (:baseurl site-config) (string/join "/" path) "/")}
            (last path)]])]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Namespace page

(defn emit-alphabetized-links
  [records]
  (let [segments (group-by (comp str first :name) records)]
    (for [k (sort (keys segments))]
      (list [:h3 (string/capitalize k)]
            [:p
             (for [r (sort-by :name (get segments k))]
               [:a {:href (:url r) :style "padding: 0 0.2em;"} (:name r)])]))))

(defn namespace-page [version namespace]
  (layout
   site-config
   (util/markdown-file (str "resources/" version "/" namespace "/index.md"))
   [:h1 {:class "page-title"}
    [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]
    (-> site-config :style :header-sep)
    namespace]
   (let [keys                  ["special"        "macro"   "fn"         "var"]
         mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
         ids      (zipmap keys ["sforms",        "macros", "fns",       "vars"])
         link-ids (zipmap keys ["sff",           "mf",     "ff",        "vf"])
         grouping (->> (for [path (util/paths version namespace)]
                         (let [fp (string/join "/" path)]
                           {:url  (str (:baseurl site-config) fp "/")
                            :name (slurp (io/resource (str fp "/name.txt")))
                            :type (slurp (io/resource (str fp "/type.txt")))}))
                       (group-by :type))]
     (for [k keys]
       (when-let [records (get grouping k)]
         (list
          [:h2 (get mapping k) " "
           [:a {:id (get link-ids k)} "+"]]
          [:div {:id (get ids k)}
           (emit-alphabetized-links records)]))))

   [:script {:src "/public/jquery.js" :type "text/javascript"}]
   [:script {:src "/public/namespace.js" :type "text/javascript"}]))

(def namespace-page-memo
  (memoize namespace-page))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Symbol page

(defn example [index path]
  (let []
    (list
     [:h4 "Example " (inc index)]
     [:div (util/clojure-file path)])))

(defn symbol-page [version namespace symbol type]
  (let [symbol-file-path (partial str "resources/" version "/" namespace "/" symbol "/")
        name             (slurp (symbol-file-path "name.txt"))]
    (case type
      :html
      (layout
       site-config
       [:h1 {:class "page-title"}
        [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]
        (-> site-config :style :header-sep)
        [:a {:href (str (:baseurl site-config) version "/" namespace "/")} namespace]
        (-> site-config :style :header-sep)
        name]
       [:h2 "Arities"]
       [:p (-> "arities.txt" symbol-file-path util/resource-file-contents)]
       [:h2 "Official Documentation"]
       [:pre (-> "docstring.md" symbol-file-path util/markdown-file)]
       (when-let [comdoc  (-> "extended-docstring.md" symbol-file-path util/markdown-file)]
         (list
          [:h2 "Community Documentation"]
          [:pre comdoc]))
       (when-let [examples (-> "examples" symbol-file-path util/dir-list-as-strings seq)]
         (list
          [:h2 "Examples"]
          (map-indexed example examples)))
       (when-let [source (-> "source.clj" symbol-file-path util/clojure-file)]
         (list
          [:h2 "Source"]
          [:div source])))
      :text
      (-> (str version " - " namespace " - " name)
          response/response
          (response/content-type "text/plain")))))
