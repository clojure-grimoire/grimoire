(ns grimoire.web.views
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [compojure.core :refer [GET]]
            [grimoire.github :as gh]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as util]
            [grimoire.util :as gutil]
            [ring.util.response :as response]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Site config

(def site-config
  {:url                 "http://grimoire.arrdem.com"
   :repo                "https://github.com/clojure-grimoire/grimoire"
   :baseurl             "/"
   :version             (slurp "VERSION")
   :clojure-version     "1.6.0"
   :google-analytics-id "UA-44001831-2"
   :year                "2014"
   :author              {:me          "http://arrdem.com/"
                         :email       "me@arrdem.com"
                         :gittip      "https://gittip.com/arrdem/"}
   :style               {:header-sep  " / "
                         :title       "Grimoire - Clojure Documentation"
                         :description "Community documentation of Clojure"
                         :quote       "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."}})
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; 404 Error page

(defn error-404 []
  (layout
   site-config
   (slurp (io/resource "404.html"))))

(defn error-unknown-version [version]
  (layout
   site-config
   [:h1 {:class "page-title"}
    [:a "Clojure " version]]
   [:p "Unknown Clojure version " (pr-str [version])]
   [:p "If you found a broken link, please report the issue encountered on the github bugtracker."]))

(defn error-unknown-namespace [version namespace]
  (layout
   site-config
   [:h1 {:class "page-title"}
    [:a "Clojure " version]]
   [:p "Unknown namespace identifier " (pr-str [version namespace])]
   [:p "If you found a broken link, please report the issue encountered on the github bugtracker."]))

(defn error-unknown-symbol [version namespace symbol]
  (layout
   site-config
   [:h1 {:class "page-title"}
    [:a "Clojure " version]]
   [:p "Unknown symbol identifier " (pr-str [version namespace symbol])]
   [:p "If you found a broken link, please report the issue encountered on the github bugtracker."]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Generic markdown page

(defn markdown-page [page]
  (let [[header page] (util/parse-markdown-page page)]
    (layout
     site-config
     (if page
       (list (when-let [title (:title header)]
               [:h1 title])
             page)
       (response/not-found "Resource not found, sorry. Please file an issue on the github bugtracker.")))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Home page

(defn home-page []
  (layout
   site-config
   [:blockquote [:p (-> site-config :style :quote)]]
   (util/cheatsheet-memo site-config)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Version page

(defn version-page [version]
  (let [rel-notes-file (str "resources/org.clojure/clojure/" version "/release-notes.md")]
    (layout
     (assoc site-config
       :page {:description (str "Clojure " version " release information")})
     [:h1 {:class "page-title"}
      [:a {:href (str (:baseurl site-config) version "/")} "Clojure " version]]

     [:h2 "Release Notes - "
      [:a {:href (gh/->edit-url site-config "develop" rel-notes-file)} "edit"]]
     (util/markdown-file rel-notes-file)

     [:h2 "Namespaces"]
     [:ul
      (for [path (->> version util/paths (sort-by last))
            :when (not (= "release-notes.md" (last path)))]
        [:li
         [:a {:href (str (:baseurl site-config)
                         (string/join "/" (drop 2 path))
                         "/")}
          (last path)]])])))

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
  (let [ns-dir        (str "resources/org.clojure/clojure/" version "/" namespace)
        ns-notes-file (str "resources/org.clojure/clojure/" version "/" namespace "/ns-notes.md")]
    (when (.isDirectory (io/file ns-dir))
      (layout
       (assoc site-config
         :page {:description (str "Clojure " version " " namespace " namespace symbols list")})
       [:h1 {:class "page-title"}
        [:span {:style "display:inline-block;"}
         [:a {:href "../"} "Clojure " version]
         (-> site-config :style :header-sep)]
        namespace]
       [:h2 "Namespace Notes - "
        [:a {:href (gh/->edit-url site-config "develop" ns-notes-file)} "edit"]]
       (util/markdown-file ns-notes-file)
       [:h2 "Symbols"]
       (let [keys                  ["special",       "macro",  "fn",        "var"]
             mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
             ids      (zipmap keys ["sforms",        "macros", "fns",       "vars"])
             link-ids (zipmap keys ["sff",           "mf",     "ff",        "vf"])
             grouping (->> (for [path (util/paths version namespace)
                               :when (not (= "ns-notes.md" (last path)))]
                           (let [fp (string/join "/" path)
                                 legacy-path (string/join "/" (drop 2 path))]
                             {:url  (str (:baseurl site-config) legacy-path "/")
                              :name (slurp (io/resource (str fp "/name.txt")))
                              :type (slurp (io/resource (str fp "/type.txt")))}))
                         (group-by :type))]
         (for [k keys]
           (when-let [records (get grouping k)]
             (list
              (let [links (emit-alphabetized-links records)]
                [:div.section
                 [:h3.heading (get mapping k)
                  " " [:span.unhide (if (< 6 (count links)) "+" "-")]]
                 [:div {:class (str "autofold"
                                    (when (< 6 (count links))
                                      " prefold"))}
                  links]])))))

       [:script {:src "/public/jquery.js" :type "text/javascript"}]
       [:script {:src "/public/fold.js" :type "text/javascript"}]))))

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
      "Example " (inc index) " - "
      [:a {:href (gh/->edit-url site-config "develop" path)} "edit"]
      [:div (util/clojure-file path)]])))

(defn raw-example [index path]
  (str "Example " (inc index) "\n"
       "----------------------------------------\n"
       (util/resource-file-contents path)
       "\n"))

(defn all-examples
  [top-version namespace symbol type]
  (let [path (str namespace "/" symbol "/examples/")]
    (case type
      :html
      ,,(for [v (clojure-example-versions top-version)]
          (let [examples-dir (str "resources/org.clojure/clojure/" v "/" path)
                examples     (util/dir-list-as-strings examples-dir)]
            (when (or (not (empty? examples))
                     (= "1.6.0" v))
              (list
               [:div.section
                [:h3.heading "Examples from Clojure " v " " [:span.unhide "+"]]
                [:div.autofold.prefold
                 (map-indexed example examples)
                 [:a {:href (gh/->new-url site-config "develop" examples-dir)}
                  "Contribute an example!"]]]))))

      :text
      ,,(->> (for [v (clojure-example-versions top-version)]
             (let [examples (util/dir-list-as-strings (str "resources/org.clojure/clojure/" v "/" path))]
               (when-not (empty? examples)
                 (str "### Examples from Clojure " v "\n"
                      "----------------------------------------\n"
                      (->> examples
                         (map-indexed raw-example)
                         (interpose "\n")
                         (apply str))))))
           (interpose "\n")
           (apply str)))))

(defn symbol-page
  [version namespace symbol type]
  (let [root             (str "resources/org.clojure/clojure/" version "/" namespace "/" symbol "/")
        symbol-file-path (partial str root)
        name-file        (-> "name.txt"              symbol-file-path)
        type-file        (-> "type.txt"              symbol-file-path)
        arities-file     (-> "arities.txt"           symbol-file-path)
        docstring-file   (-> "docstring.md"          symbol-file-path)
        comdoc-file      (-> "extended-docstring.md" symbol-file-path)
        source-file      (-> "source.clj"            symbol-file-path)
        related-file     (-> "related.txt"           symbol-file-path)]
    (case type
      (:html :text/html)
      ,,(when (.isDirectory (io/file root))
          (let [name (slurp name-file)]
            (layout
             (assoc site-config
               :page {:description (str "Clojure " version " " namespace "/" symbol
                                        " documentation and examples")
                      :summary (slurp docstring-file)})
             [:h1 {:class "page-title"}
              [:span {:style "display:inline-block;"}
               [:a {:href "../../"} "Clojure " version]
               (-> site-config :style :header-sep)]
              [:span {:style "display:inline-block;"}
               [:a {:href "../"} namespace]
               (-> site-config :style :header-sep)]
              name]

             [:h2 "Arities"]
             [:p (util/resource-file-contents arities-file)]
             [:h2 "Official Documentation - "
              [:a {:href (gh/->edit-url site-config "develop" docstring-file)}
               "edit"]]
             [:pre (util/resource-file-contents docstring-file)]

             (when-let [comdoc (util/markdown-file comdoc-file)]
               (list
                [:h2 "Community Documentation - "
                 [:a {:href (gh/->edit-url site-config "develop" comdoc-file)}
                  "edit"]]
                comdoc))

             (when-let [examples (all-examples version namespace symbol :html)]
               [:div.section
                [:h2.heading "Examples " [:span.unhide "+"]]
                [:div.autofold.prefold
                 examples
                 (when-not (= "special" (slurp type-file))
                   [:a {:href (str "http://crossclj.info/fun/" namespace "/" (util/url-encode name) ".html")}
                    [:h3 "Uses on crossclj"]])]])

             (when-let [file (io/resource related-file)]
               (let [related (line-seq (io/reader file))]
                 (list [:h2 "Related"]
                       [:ul (for [r related]
                              (let [[ns sym] (string/split r #"/")]
                                [:li [:a {:href (str (:baseurl site-config)
                                                     "/" version "/" ns "/"
                                                     (gutil/my-munge sym) "/")}
                                      r]]))])))

             (when-let [source (util/clojure-file source-file)]
               (list
                [:h2 "Source"]
                [:div source]))

             [:script {:src "/public/jquery.js" :type "text/javascript"}]
             [:script {:src "/public/fold.js" :type "text/javascript"}])))

      (:text :text/plain)
      ,,(let [line80           (apply str (repeat 80 "-"))
              line40           (apply str (repeat 40 "-"))]
          (when (.isDirectory (io/file root))
            (-> (str "# "version " - " namespace " - " (slurp name-file) "\n"
                                        ;line80
                    "\n"

                    "## Arities\n"
                                        ;line40 "\n"
                    (util/resource-file-contents arities-file)
                    "\n"

                    "## Documentation\n"
                                        ;line40 "\n"
                    (util/resource-file-contents docstring-file)
                    "\n"

                    "## User Documentation\n"
                                        ;line40 "\n"
                    (util/resource-file-contents comdoc-file)
                    "\n"

                    "## Examples\n"
                                        ;line40 "\n"
                    (all-examples version namespace symbol :text)

                    "## See Also\n"
                                        ;line40 "\n"
                    (util/resource-file-contents related-file))
               response/response
               (response/content-type "text/plain")))))))
