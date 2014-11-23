(ns grimoire.web.views
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [grimoire.github :as gh]
            [grimoire.util :as util]
            [grimoire.api :as api]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil :refer [clojure-versions]] ; FIXME: remove
            [clj-semver.core :as semver]                            ; FIXME: remove
            [ring.util.response :as response]
            [detritus.text :as text]))

(def site-config
  {:url                 "http://conj.io"
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

(defn home-page []
  (layout
   site-config
   [:blockquote [:p (-> site-config :style :quote)]]
   (wutil/cheatsheet-memo site-config)))

(defn link-to [prefix x]
  {:href (str prefix (:uri x))})

(def link-to' (partial link-to "/store/"))

(defn articles-list []
  (layout site-config
          [:h1 {:class "page-title"} "Articles"]
          [:ul
           (for [p     (->> (wutil/paths "articles") sort)
                 :let  [[_articles a] p
                        a             (string/replace a ".md" "")]]
             [:li [:a (link-to "articles" a) a]])]))

(defmulti header :type)

(defmethod header :group [group]
  [:a (link-to' group)
   ,,(:name group)])

(defmethod header :artifact [artifact]
  (list (header (:parent artifact))
        "/" [:a (link-to' artifact)
             ,,,(:name artifact)]))

(defmethod header :version [version]
  (list "[" (header (:parent version))
        " " [:a (link-to' version)
             ,,,(pr-str (:name version))] "]"))

(defmethod header :namespace [namespace]
  (list (header (:parent namespace)) " "
        [:a (link-to' namespace)
         ,,,(:name namespace)]))

(defmethod header :def [symbol]
  (let [sym'   (util/munge (:name symbol))]
    (list (header (:parent symbol)) "/"
          [:a (link-to' symbol)
           ,,,(:name symbol)])))

(defmulti store-page identity)

;; FIXME: text/zalgo
;; FIXME: text/plain
;; FIXME: application/json
;; FIXME: application/edn

(defmethod store-page :text/html [_]
  (layout site-config
          [:h1 {:class "page-title"}
           ,,"Artifact store"]
          [:h2 "Known Maven groups"]
          [:ul
           (for [group (->> (api/list-groups site-config)
                            (sort-by :name))]
             [:li [:a (link-to' group) (:name group)]])]))


(def dispatch-fn
  (fn [x y]
    {:pre [(and x y)
           (keyword? x)]}
    x))

(defmulti group-page dispatch-fn)

;; FIXME: text/zalgo
;; FIXME: application/edn
;; FIXME: application/json

(defmethod group-page :text/html [_ group-thing]
  (layout site-config
          [:h1 {:class "page-title"} (header group-thing)]
          [:h2 "Known artifacts"]
          [:ul
           (for [artifact (->> (api/list-artifacts site-config group-thing)
                               (sort-by :name))
                 :let  [group (:parent artifact)]]
             [:li [:a (link-to' artifact)
                   (format "%s/%s" (:name group) (:name artifact))]])]))



(defmulti artifact-page dispatch-fn)

;; FIXME: text/zalgo
;; FIXME: text/plain
;; FIXME: application/edn
;; FIXME: application/json

(defmethod artifact-page :text/html [_ artifact-thing]
  (layout site-config
          [:h1 {:class "page-title"} (header artifact-thing)]
          [:h2 "Known release versions"]
          [:ul
           (for [version (->> (api/list-versions site-config artifact-thing)
                              (sort-by :name)
                              reverse)
                 :let  [artifact (:parent version)
                        group    (:parent artifact)]]
             [:li [:a (link-to' version) (format "[%s/%s \"%s\"]"
                                                 (:name group)
                                                 (:name artifact)
                                                 (:name version))]])]))


(defmulti version-page dispatch-fn)

;; FIXME: text/zalgo
;; FIXME: text/plain
;; FIXME: application/edn
;; FIXME: application/json

(defmethod version-page :text/html [_ version-thing]
  (let [[[v notes]] (api/read-notes site-config version-thing)]
    (layout
     site-config ;; FIXME: add artifact & group name to title somehow?
     [:h1 {:class "page-title"} (header version-thing)]
     (when notes
       (list [:h2 "Release Notes"]
             (wutil/markdown-string notes)))

     [:h2 "Namespaces"]
     [:ul
      (for [ns-thing (->> (api/list-namespaces site-config version-thing)
                          (sort-by :name))]
        [:li
         [:a (link-to' ns-thing) (:name ns-thing)]])])))


(defn emit-alphabetized-links [records]
  (let [segments (group-by (comp first str :name) records)]
    (for [k (sort (keys segments))]
      (list [:h4 (string/capitalize k)]
            [:p (for [r (sort-by :name (get segments k))]
                  [:a {:href (:url r) :style "padding: 0 0.2em;"} (:name r)])]))))

(defmulti namespace-page dispatch-fn)

;; FIXME: text/zalgo
;; FIXME: text/plain
;; FIXME: application/edn
;; FIXME: application/json

(defmethod namespace-page :text/html [_ namespace-thing]
  (layout
   site-config ;; FIXME: add artifact, namespace?
   [:h1 {:class "page-title"}
    (header namespace-thing)]

   (let [{:keys [doc name] :as meta} (api/read-meta site-config namespace-thing)]
     (when doc
       (list [:h2 "Namespace Docs"]
             [:p (-> doc
                     text/text->paragraphs
                     text/render)])))

   (when-let [notes (first
                     (for [[v notes] (api/read-notes site-config namespace-thing)
                           :when     (= v (:name (:parent namespace-thing)))]
                       notes))]
     (list [:h2 "Namespace Notes"]
           [:p (wutil/markdown-string notes)]))

   (list [:h2 "Symbols"]
         ;; FIXME: the fuck am I doing here srsly
         (let [keys     [:special :macro :fn :var]
               mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
               ids      (zipmap keys ["sforms", "macros", "fns", "vars"])
               link-ids (zipmap keys ["sff", "mf", "ff", "vf"])
               grouping (->> (for [def-thing (api/list-defs site-config namespace-thing)
                                   :let      [meta (api/read-meta site-config def-thing)]]
                               {:url  (:href (link-to' def-thing))
                                :name (:name meta)
                                :type (:type meta)})
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
                    links]]))))))

   [:script {:src "/public/jquery.js" :type "text/javascript"}]
   [:script {:src "/public/fold.js" :type "text/javascript"}]))


;; FIXME: this should be a smarter cache
(def namespace-page-memo
  (memoize namespace-page))


(defmulti symbol-page dispatch-fn)

;; FIXME: text/zalgo
;; FIXME: application/edn
;; FIXME: application/json

(defmethod symbol-page :text/html [_ def-thing]
  (let [groupid      (t/thing->group     def-thing)
        artifactid   (t/thing->artifact  def-thing)
        version      (t/thing->version   def-thing)
        namespace    (t/thing->namespace def-thing)

        {:keys [doc name type arglists src]
         :as   meta} (api/read-meta site-config def-thing)

        doc          (-> doc
                         text/text->paragraphs
                         text/render)

        notes        (-> site-config (api/read-notes def-thing)) ;; Seq [version, notes]

        related      (-> site-config
                         (api/read-related def-thing))]  ;; Seq [version, related]
    (when doc
      (layout
       (assoc site-config
         :page {:description (str "Clojure " version " " namespace "/" symbol
                                  " documentation and examples")
                :summary doc})
       [:h1 {:class "page-title"}
        (header def-thing)]

       (when arglists
         (list [:h2 "Arities"]
               [:pre (->> arglists
                          (map pr-str)
                          (map #(str "   " %))
                          (interpose \newline))]))

       (when doc
         (list [:h2 "Official Documentation"]
               [:pre doc]))

       (when notes
         (list
          [:h2 "Community Documentation"]
          ;; FIXME: Add edit URL!
          (for [[v text] notes]
            [:p (-> text
                    text/text->paragraphs
                    text/render)])))

       ;; FIXME: examples needs a _lot_ of work
       (when-let [examples (api/read-examples site-config def-thing)]
         [:div.section
          [:h2.heading "Examples " [:span.hide "-"]]
          [:div.autofold
           (for [[v e] examples]
             [:div.example
              [:div.source
               (wutil/highlight-clojure e)]])
           ;; FIXME: Add example link!
           ]])


       (when-not (= :special type)
         [:a {:href (str "http://crossclj.info/fun/" (:name namespace) "/" (wutil/url-encode name) ".html")}
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
            (wutil/highlight-clojure src)]]))

       [:script {:src "/public/jquery.js" :type "text/javascript"}]
       [:script {:src "/public/fold.js" :type "text/javascript"}]))))


(defmethod symbol-page :text/plain [_ def-thing]
  (let [groupid      (t/thing->group     def-thing)
        artifactid   (t/thing->artifact  def-thing)
        version      (t/thing->version   def-thing)
        namespace    (t/thing->namespace def-thing)

        {:keys [doc name type arglists src]
         :as   meta} (api/read-meta site-config def-thing)

        doc          (-> doc
                         text/text->paragraphs
                         text/render)

        notes        (-> site-config (api/read-notes def-thing)) ;; Seq [version, notes]

        related      (-> site-config
                         (api/read-related def-thing))  ;; Seq [version, related]
        
        line80 (apply str (repeat 80 "-"))
        line40 (apply str (repeat 40 "-"))]
    (when doc
      ;; FIXME: else what? doesn't make sense w/o doc...
      ;; FIXME: conditionalize _all_ headers
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
               "FIXME: This version of Grimoire can't read examples for text format!\n"

               "## See Also\n"
               ;; line40 "\n"
               related)
          response/response
          (response/content-type "text/plain")))))
