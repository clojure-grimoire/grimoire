(ns grimoire.web.views
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [compojure.core :refer [GET]]
            [grimoire.github :as gh]
            [grimoire.util :as util]
            [grimoire.api :as api]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil :refer [clojure-versions]]
            [clj-semver.core :as semver]
            [ring.util.response :as response]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Site config

(def site-config
  {:url                 "http://grimoire.arrdem.com"
   :repo                "https://github.com/clojure-grimoire/grimoire"
   :baseurl             "/"
   :datastore           {:docs  "doc-store"
                         :notes "notes-store"}
   :version             (slurp "VERSION")
   :clojure-version     "1.6.0"
   :google-analytics-id "UA-44001831-2"
   :year                "2014"
   :author              {:me          "http://arrdem.com/"
                         :email       "me@arrdem.com"
                         :gittip      "https://gittip.com/arrdem/"}
   :style               {:header-sep  "/"
                         :title       "Grimoire - Community Clojure Documentation"
                         :description "Community documentation of Clojure"
                         :quote       "Even the most powerful wizard must consult grimoires as an aid against forgetfulness."}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Generic markdown page

(defn markdown-page [page]
  (let [[header page] (wutil/parse-markdown-page page)]
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
   (wutil/cheatsheet-memo site-config)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Shared header generator

(defn link-to [& args]
  {:href (->> args
              (interpose \/ )
              (cons (:baseurl site-config))
              (apply str))})

(def link-to' (partial link-to "store"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Articles list page

(defn articles-list []
  (layout site-config
          [:h1 {:class "page-title"} "Articles"]
          [:ul
           (for [p     (->> (wutil/paths "articles") sort)
                 :let  [[_articles a] p
                        a             (string/replace a ".md" "")]]
             [:li [:a (link-to "articles" a) a]])]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Header helper

(defn header
  ([groupid]
     [:a (link-to' groupid) groupid])

  ([groupid artifactid]
     (let [params [groupid artifactid]]
       (list (apply header (butlast params))
             "/" [:a (apply link-to' params) artifactid])))

  ([groupid artifactid version]
     (let [params [groupid artifactid version]]
       (list "[" (apply header (butlast params))
             " " [:a (apply link-to' params) (pr-str version)] "]")))

  ([groupid artifactid version namespace]
     (let [params [groupid artifactid version namespace]]
       (list (apply header (butlast params)) " "
             [:a (apply link-to' params) namespace])))

  ([groupid artifactid version namespace symbol]
     (let [sym'   (util/munge symbol)
           params [groupid artifactid version namespace sym']]
       (list (apply header (butlast params)) "/"
             [:a (apply link-to' params) symbol]))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; API page

(defn api-page []
  (layout
   site-config
   [:h1 {:class "page-title"} "Documentation store"]
   [:h2 "Known Maven groups"]
   [:ul
    (for [[_ groupid] (->> (wutil/paths "store") sort)]
      [:li [:a (link-to' groupid) groupid]])]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Groupid page

(defn groupid-page [groupid]
  (layout site-config
          [:h1 {:class "page-title"} "Group " (header groupid)]
          [:h2 "Known artifacts"]
          [:ul
           (for [p     (->> (wutil/paths "store" groupid) sort)
                 :let  [[_ groupid artifactid] p]]
             [:li [:a (link-to' groupid artifactid)
                   (pr-str (symbol groupid artifactid))]])]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Artifactid page

(defn artifactid-page [groupid artifactid]
  (layout site-config
          [:h1 {:class "page-title"} "Artifact " (header groupid artifactid)]
          [:h2 "Known release versions"]
          [:ul
           (for [p     (->> (wutil/paths "store" groupid artifactid) sort)
                 :let  [[_ groupid artifactid version] p]]
             [:li [:a (link-to' groupid artifactid version)
                   (pr-str [(symbol groupid artifactid) version])]])]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Version page

(defn version-page [groupid artifactid version]
  (let [rel-notes-file (wutil/resource-file version "release-notes.md")]
    (layout
     (assoc site-config
       :page {:description (str "Clojure " version " release information")})
     [:h1 {:class "page-title"} (header groupid artifactid version)]
     [:h2 "Release Notes - "
      [:a {:href (gh/->edit-url site-config "develop" rel-notes-file)} "edit"]]
     (wutil/markdown-file rel-notes-file)

     [:h2 "Namespaces"]
     [:ul
      (for [path (->> (wutil/paths "store" groupid artifactid version)
                      (sort-by last))
            :when (not (= "release-notes.md" (last path)))]
        [:li
         [:a (apply link-to' (rest path))
          (last path)]])])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Namespace page

(defn emit-alphabetized-links [records]
  (let [segments (group-by (comp str first :name) records)]
    (for [k (sort (keys segments))]
      (list [:h4 (string/capitalize k)]
            [:p
             (for [r (sort-by :name (get segments k))]
               [:a {:href (:url r) :style "padding: 0 0.2em;"} (:name r)])]))))

(defn namespace-page [groupid artifactid version namespace]
  (let [resource      (partial wutil/resource-file groupid artifactid version namespace)
        ns-dir        (resource "")
        ns-notes-file (resource "ns-notes.md")]
    (when (.isDirectory (io/file ns-dir))
      (layout
       (assoc site-config
         :page {:description (str "Clojure " version " " namespace " namespace symbols list")})
       [:h1 {:class "page-title"}
        (header groupid artifactid version namespace)]
       [:h2 "Namespace Notes - "
        [:a {:href (gh/->edit-url site-config "develop" ns-notes-file)} "edit"]]
       (wutil/markdown-file ns-notes-file)
       [:h2 "Symbols"]
       (let [keys                  ["special",       "macro",  "fn",        "var"]
             mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
             ids      (zipmap keys ["sforms",        "macros", "fns",       "vars"])
             link-ids (zipmap keys ["sff",           "mf",     "ff",        "vf"])
             grouping (->> (for [path  (wutil/paths "store" groupid artifactid version namespace)
                                 :when (not (= "ns-notes.md" (last path)))]
                             (let [fp          (string/join "/" path)
                                   legacy-path (string/join "/" (drop 2 path))]
                               {:url  (str (:baseurl site-config) fp "/")
                                :name (slurp (io/resource (str fp "/name.txt")))
                                :type (slurp (io/resource (str fp "/type.txt")))}))
                           (group-by :type))]
         (for [k keys]
           (when-let [records (get grouping k)]
             (list
              (let [links (emit-alphabetized-links records)]
                [:div.section
                 [:h3.heading (get mapping k)
                  " " (if (< 6 (count links))
                        [:span.unhide "+"]
                        [:span.hide "-"])]
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

(defn example [index [clojure path]]
  [:div.example
   [:h3
    [:span.title "Example " (inc index)]
    [:span.edit  {"style" "float: right;"}
     [:a {:href (gh/->edit-url site-config "develop" path)} "edit"]]
    [:span.version {"style" "float: right; padding-right: 1.0em;"}
     "Clojure " clojure]]
   [:div.source (wutil/clojure-file path)]])

(defn raw-example [index path]
  (str "Example " (inc index) "\n"
       "----------------------------------------\n"
       (wutil/resource-file-contents path)
       "\n"))

(defn all-examples
  [groupid artifactid top-version namespace symbol type]
  (let [path     (str "resources/store/" groupid "/" artifactid "/%s/"
                      namespace "/" symbol "/examples/")
        versions (->> clojure-versions
                      (filter (fn [v]
                                (or (= v top-version)
                                    (semver/older? v top-version))))
                      sort)]

    (case type
      :html
      ,,(for [v     versions
              :let  [examples-dir (format path v)]
              e     (wutil/dir-list-as-strings examples-dir)]
          [v e])

      :text
      ,,(->> (for [v versions]
               (let [d        (format path v)
                     examples (wutil/dir-list-as-strings d)]
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
  [symbol-thing]
  (let [groupid      (t/thing->group symbol-thing)
        artifactid   (t/thing->artifact symbol-thing)
        version      (t/thing->version symbol-thing)
        namespace    (t/thing->namespace symbol-thing)
        
        {:keys [doc name arglists type src]
         :as   meta} (api/read-meta site-config symbol-thing)
        
        notes        (api/read-notes site-config symbol-thing)
        related      (api/read-related site-config symbol-thing)]
    (case type
      (:html :text/html "text/html")
      ,,(when doc
          (layout
           (assoc site-config
             :page {:description (str "Clojure " version " " namespace "/" symbol
                                      " documentation and examples")
                    :summary doc})
           [:h1 {:class "page-title"}
            (header groupid artifactid version namespace name)]

           (when arglists
             (list [:h2 "Arities"]
                   [:pre (map pr-str arglists)]))

           (when docs
             (list [:h2 "Official Documentation - "
                    ;; FIXME: where does this URL go?
                    [:a {:href (gh/->edit-url site-config "develop" docstring-file)}
                     "edit"]]
                   [:pre (wutil/resource-file-contents docstring-file)]))

           (when notes
             (list
              [:h2 "Community Documentation - "
               ;; FIXME: where does this URL go?
               [:a {:href (gh/->edit-url site-config "develop" comdoc-file)}
                "edit"]]
              (util/markdown-string notes)))

           (when-let [examples (all-examples groupid artifactid version
                                             namespace symbol :html)]
             [:div.section
              [:h2.heading "Examples " [:span.hide "-"]]
              [:div.autofold
               (list (map-indexed example examples)

                     ;; FIXME: this needs to be dynamic not static to my store
                     [:h3 [:a {:href (gh/->new-url site-config "develop"
                                                   (format "resources/store/%s/%s/%s/%s/%s/examples/"
                                                           groupid artifactid version
                                                           namespace symbol))}
                           "Contribute an example!"]])]])


           (when-not (= :special type)
             [:a {:href (str "http://crossclj.info/fun/" namespace "/" (wutil/url-encode name) ".html")}
              [:h2 "Uses on crossclj"]])

           (when related
             (list [:h2 "Related Symbols"]
                   [:ul (for [r related]
                          (let [[ns sym] (string/split r #"/")]
                            [:li [:a {:href (str (:baseurl site-config)
                                                 "/" version "/" ns "/"
                                                 (util/munge sym) "/")}
                                  r]]))]))

           (when src
             (list
              [:div.section
               [:h2.heading "Source " [:span.unhide "+"]]
               [:div.autofold.prefold
                (util/highlight-clojure src)]]))

           [:script {:src "/public/jquery.js" :type "text/javascript"}]
           [:script {:src "/public/fold.js" :type "text/javascript"}]))

      (:text :text/plain "text/plain" "text")
      ,,(let [line80 (apply str (repeat 80 "-"))
              line40 (apply str (repeat 40 "-"))]
          (when doc
            (-> (str (format "# [%s/%s \"%s\"] %s/%s\n" groupid artifactid version namespace name)
                     ;; line80
                     "\n"

                     "## Arities\n"
                     ;; line40 "\n"
                     (->> (map #(format "  %s\n" (pr-str %1)))
                          (apply str))
                     "\n"

                     "## Documentation\n"
                     ;; line40 "\n"
                     doc
                     "\n"

                     "## User Documentation\n"
                     ;; line40 "\n"
                     notes
                     "\n"

                     "## Examples\n"
                     ;; line40 "\n"
                     (all-examples groupid artifactid version namespace symbol :text)
                     
                     "## See Also\n"
                     ;; line40 "\n"
                     related)
                response/response
                (response/content-type "text/plain")))))))
