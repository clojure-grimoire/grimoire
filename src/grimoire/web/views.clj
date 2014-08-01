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
   :style               {:header-sep " / "}})

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
   [:h1 {:class "page-title"}
    [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]]
   [:h2 "Release Notes"
    " - "
    [:a {:href (str "https://github.com/arrdem/grimoire/edit/develop/resources/"
                    version "/release-notes.md")}
     "edit"]]
   (util/markdown-file (str "resources/" version "/release-notes.md"))
   [:h2 "Namespaces"]
   [:ul
    (for [path (->> version util/paths (sort-by last))]
      [:li [:a {:href (str (:baseurl site-config) (string/join "/" path) "/")}
            (last path)]])]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Namespace page

(defn emit-alphabetized-links
  [records]
  (let [segments (group-by (comp str first :name) records)]
    (for [k (sort (keys segments))]
      (list [:h4 (string/capitalize k)]
            [:p
             (for [r (sort-by :name (get segments k))]
               [:a {:href (:url r) :style "padding: 0 0.2em;"} (:name r)])]))))

(defn namespace-page [version namespace]
  (layout
   site-config
   [:h1 {:class "page-title"}
    [:a {:href "../"} "Clojure " version]
    (-> site-config :style :header-sep)
    namespace]
   [:h2 "Namespace Notes"
    " - "
    [:a {:href (str "https://github.com/arrdem/grimoire/edit/develop/resources/"
                    version "/" namespace "/ns-notes.md")}
     "edit"]]
   (util/markdown-file (str "resources/" version "/" namespace "/ns-notes.md"))
   [:h2 "Symbols"]
   (let [keys                  ["special",       "macro",  "fn",        "var"]
         mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
         ids      (zipmap keys ["sforms",        "macros", "fns",       "vars"])
         link-ids (zipmap keys ["sff",           "mf",     "ff",        "vf"])
         grouping (->> (for [path (util/paths version namespace)
                             :when (not (= "ns-notes.md" (last path)))]
                         (let [fp (string/join "/" path)]
                           {:url  (str (:baseurl site-config) fp "/")
                            :name (slurp (io/resource (str fp "/name.txt")))
                            :type (slurp (io/resource (str fp "/type.txt")))}))
                       (group-by :type))]
     (for [k keys]
       (when-let [records (get grouping k)]
         (list
          [:h3 (get mapping k) " "
           [:a {:id (get link-ids k)} "+"]]
          [:div {:id (get ids k)}
           (emit-alphabetized-links records)]))))

   [:script {:src "/public/jquery.js" :type "text/javascript"}]
   [:script {:src "/public/namespace.js" :type "text/javascript"}]))

(def namespace-page-memo
  (memoize namespace-page))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Symbol page

(def clojure-example-versions
  {"1.6.0" ["1.6.0" "1.5.0" "1.4.0"]
   "1.5.0" ["1.5.0" "1.4.0"]
   "1.4.0" ["1.4.0"]})

(defn example [index path]
  (let []
    (list
     [:h4
      "Example " (inc index)
      " - "
      [:a {:href (str "https://github.com/arrdem/grimoire/edit/develop/" path)} "edit"]
     [:div (util/clojure-file path)]])))

(defn raw-example [index path]
  (str "Example " (inc index) "\n"
       "----------------------------------------\n"
       (util/resource-file-contents path)
       "\n"))

(defn all-examples
  [top-version namespace symbol type]
  (case type
    :html
    (let [path (str namespace "/" symbol "/examples/")]
      (for [v (clojure-example-versions top-version)]
        (let [examples (util/dir-list-as-strings (str "resources/" v "/" path))]
          (list
           [:h2 "Examples from Clojure " v]
           (map-indexed example examples)
           [:a {:href (str "https://github.com/arrdem/grimoire/new/develop/" v "/" path)}
            "Contribute an example!"]))))

    :text
    (let [path (str namespace "/" symbol "/examples/")]
      (->> (for [v (clojure-example-versions top-version)]
             (let [examples (util/dir-list-as-strings (str "resources/" v "/" path))]
               (str "### Examples from Clojure " v "\n"
                    "----------------------------------------\n"
                    (->> examples
                         (map-indexed raw-example)
                         (interpose "\n")
                         (apply str)))))
           (interpose "\n")
           (apply str)))))

(defn symbol-page
  [version namespace symbol type]
  (let [symbol-file-path (partial str "resources/" version "/" namespace "/" symbol "/")
        name             (slurp (symbol-file-path "name.txt"))]
    (case type
      (:html :text/html)
      (layout
       site-config
       [:h1 {:class "page-title"}
        [:a {:href "../"} "Clojure " version]
        (-> site-config :style :header-sep)
        [:a {:href "."} namespace]
        (-> site-config :style :header-sep)
        name]
       [:h2 "Arities"]
       [:p (-> "arities.txt" symbol-file-path util/resource-file-contents)]
       [:h2 "Official Documentation"
        " - "
        [:a {:href (str "https://github.com/arrdem/grimoire/edit/develop/"
                        (-> "docstring.md" symbol-file-path))}
         "edit"]]
       [:pre "  " (-> "docstring.md" symbol-file-path util/resource-file-contents)]
       (when-let [comdoc  (-> "extended-docstring.md" symbol-file-path util/markdown-file)]
         (list
          [:h2 "Community Documentation"
           " - "
           [:a {:href (str "https://github.com/arrdem/grimoire/edit/develop/"
                           (-> "extended-docstring.md" symbol-file-path))}
            "edit"]]
          comdoc))
       (when-let [examples (all-examples version namespace symbol :html)]
         (list
          [:h2 "Examples"]
          examples))
       (when-let [source (-> "source.clj" symbol-file-path util/clojure-file)]
         (list
          [:h2 "Source"]
          [:div source])))

      (:text :text/plain)
      (let [symbol-file-path (partial str "resources/" version "/" namespace "/" symbol "/")
            line80           "--------------------------------------------------------------------------------\n"
            line40           "----------------------------------------\n"]
        (-> (str "# "version " - " namespace " - " name "\n"
                 line80
                 "\n"

                 "## Arities\n"
                 line40
                 (-> "arities.txt" symbol-file-path util/resource-file-contents)
                 "\n"
                 
                 "## Documentation\n"
                 line40
                 (-> "docstring.md" symbol-file-path util/resource-file-contents)
                 "\n"

                 "## User Documentation\n"
                 line40
                 (-> "extended-docstring.md" symbol-file-path util/resource-file-contents)
                 "\n"
                 
                 "## Examples\n"
                 line40
                 "\n"
                 (all-examples version namespace symbol :text)

                 "## See Also\n"
                 line40
                 (-> "related.txt" symbol-file-path util/resource-file-contents))
            response/response
            (response/content-type "text/plain"))))))
