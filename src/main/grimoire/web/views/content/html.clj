(ns grimoire.web.views.content.html
  (:require [grimoire.web.views :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.either :refer [succeed? result]]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]
            [grimoire.web.config :as cfg]
            [grimoire.github :as gh]))


;; Helpers

(defn kv-table [f kv-seq]
  [:table
   [:tr
    [:th {:style "width: 90%;"} "Symbol"]
    [:th {:style "width: 10%;"} "View count"]]
   (for [[k v] kv-seq]
     [:tr
      [:td {:style "width: 90%;"} (f k)]
      [:td {:style "width: 10%;"} v]])])

(defn addable
  ([title link content]
   (addable :h2 title link content))

  ([title-size title link content]
   [:div.editable
    [:div {:class "header clearfix"}
     (when title [title-size title])
     [:a.edit {:href link
               :rel "nofollow"}
      "Add notes"]]
    [:div.content
     content]]))

(defn editable
  ([title link content]
   (editable :h2 title link content))

  ([title-size title link content]
   [:div.editable
    [:div {:class "header clearfix"}
     (when title [title-size title])
     [:a.edit {:href link
               :rel "nofollow"}
      "Edit"]]
    [:div.content
     content]]))

(defn edit-url' [t]
  {:pre [(or (t/note? t)
             (t/example? t))]}
  (gh/->edit-url (cfg/notes-config) "develop" (::t/file t)))

(defn ->short-url-fn [f]
  (fn [t]
    [:span (t/thing->short-string t)
     [:span {:style "float: right;"}
      [:a {:href (link-to t)} "Article"] " "
      [:a {:href (f t)} "Add"]]]))

(defn add-ex-url
  ([t]
   (->> Integer/MAX_VALUE
        rand-int
        (format "%s.clj")
        (add-ex-url t)))
  
  ([t n]
   {:pre [(t/thing? t)]}
   (let [*cfg* (cfg/lib-grim-config)
         t     (last (result (api/thing->prior-versions *cfg* t)))]
     (as-> t v
       (#'grimoire.api.fs.impl/thing->handle *cfg* :else v)
       (gh/->new-url (cfg/notes-config)
                     "develop"
                     (#'clojure.java.io/file v "examples")
                     n)))))

(defn add-note-url [t]
  {:pre [(t/thing? t)]}
  (let [*cfg* (cfg/lib-grim-config)
        t     (last (result (api/thing->prior-versions *cfg* t)))]
    (as-> t v
      (#'grimoire.api.fs.impl/thing->handle *cfg* :else v)
      (gh/->new-url (cfg/notes-config) "develop" v "notes.md"))))

(defn local-add-ex-url [t]
  {:pre [(t/thing? t)]}
  (let [*cfg* (cfg/lib-grim-config)
        t     (last (result (api/thing->prior-versions *cfg* t)))]
    (as-> t v
      (#'grimoire.api.fs.impl/thing->handle *cfg* :else v)
      (.getAbsolutePath v)
      (format "file://%s/examples/%s.clj"
              (str v) (rand-int Integer/MAX_VALUE)))))

(defn local-add-note-url [t]
  {:pre [(t/thing? t)]}
  (let [*cfg* (cfg/lib-grim-config)
        t     (last (result (api/thing->prior-versions *cfg* t)))]
    (as-> t v
      (#'grimoire.api.fs.impl/thing->handle *cfg* :notes v)
      (.getAbsolutePath v)
      (format "file://%s" (str v)))))

(defn set-funding-flag [cfg req]
  (let [{:keys [active rate]
         :or   {active false,
                rate   5}}
        (:fundraising cfg)]
    (assoc cfg :funding-flag
           (and active
                (-> req :session (:thought-about-it false) not)
                (-> req :session (:counter 1) (mod rate) (= 0))))))


;; Pages

;; FIXME: probably belongs somewhere else
(defn home-page [req]
  (layout
   (-> (cfg/site-config)
       (set-funding-flag req)
       (assoc :css ["/public/css/cheatsheet.css"]))
   ;;------------------------------------------------------------
   [:blockquote [:p (-> (cfg/site-config) :style :quote)]]
   (wutil/cheatsheet-memo (cfg/site-config))))

(def sorted-table
  #(->> %
        (sort-by second)
        reverse
        (take 100)
        (kv-table identity)))

(defn sorted-table-of [fx]
  #(->> %
        (sort-by second)
        reverse
        (take 100)
        (kv-table fx)))

(def sorted-thing-table
  (sorted-table-of wutil/mem-sts->link))

;; FIXME: this entire fuction is too datastore-aware by a lot
(defn heatmap-page []
  (layout
   (cfg/site-config)
   ;;------------------------------------------------------------
   [:h1 {:class "page-title"} "Analytics!"]
   [:p "Or at least some of it >:D"]
   (let [service @cfg/service
         db      (-> service :simpledb :db deref)
         l       (fn [x] [:div {:style "width:50%;float:left;"} x])
         r       (fn [x] [:div {:style "width:50%;float:right;"} x])]
     (list
      [:div {:style "width:100%;clear:both;"}
       (format "%s people thought about funding!"
               (get db [:funding :thought-about-it] 0))]
      (l [:div
          [:h2 "Top 100 namespaces"]
          (->> db :namespaces sorted-thing-table)])
      (l [:div
          [:h2 "Top 100 defs"]
          (->> db :defs sorted-thing-table)])
      (l [:div
          [:h2 "Top artifacts"]
          (->> db :artifacts sorted-table)])
      (l [:div
          [:h2 "Top platforms"]
          (->> db :platforms sorted-table)])
      (l [:div
          [:h2 "Top clients"]
          (->> db :clients sorted-table)])))))

(def -everything-
  (->> (api/search *cfg*
                   [:def
                    "org.clojure"
                    "clojure"
                    (t/thing->name (i "clojure.core"))
                    "clj"
                    :any
                    :any])
       (let [*cfg* (cfg/lib-grim-config)
             i     (ns-version-index)])
       (result)
       delay))

(def -no-notes-
  (delay
   (let [*cfg* (cfg/lib-grim-config)]
     (loop [[d & defs] @-everything-
            acc []]
       (if d
         (let [notes (api/read-notes *cfg* d)]
           (if (or (not (succeed? notes))
                   (and (succeed? notes)
                        (empty? (result notes))))
             (recur defs (conj acc d))
             (recur defs acc)))
         acc)))))

(def -no-examples-
  (delay
   (let [*cfg* (cfg/lib-grim-config)]
     (loop [[d & defs] @-everything-
            acc []]
       (if d
         (let [exs (api/list-examples *cfg* d)]
           (if (or (not (succeed? exs))
                   (and (succeed? exs)
                        (empty? (result exs))))
             (recur defs (conj acc d))
             (recur defs acc)))
         acc)))))

(defn worklist-page []
  (let [*cfg*   (cfg/lib-grim-config)
        service @cfg/service
        db      (-> service :simpledb :db deref)
        q!      #(get (:defs db) (t/thing->short-string %) 0)
        f       (->short-url-fn add-note-url)
        g       (->short-url-fn add-ex-url)]
    (layout
     (cfg/site-config)
     [:div {:style "width:50%;float:left"}
      [:h1 {:class "page-title"} "Symbols without notes"]
      (->> (for [d @-no-notes-] [d (q! d)])
           ((sorted-table-of f)))]

     [:div {:style "width:50%;float:left"}
      [:h1 {:class "page-title"} "Symbols without examples"]
      (->> (for [d @-no-examples-] [d (q! d)])
           ((sorted-table-of g)))])))

(defmethod store-page :text/html [req]
  (let [*lg*   (cfg/lib-grim-config)
        groups (result (api/list-groups *lg*))]
    (layout
     (-> (cfg/site-config)
         (set-funding-flag req))
     ;;------------------------------------------------------------
     [:h1 {:class "page-title"}
      ,,"Artifact store"]
     (list
      [:h2 "Known Maven groups"]
      [:ul (for [group (sort-by t/thing->name groups)]
             [:li [:a (link-to group)
                   (t/thing->name group)]])]))))

(defmethod group-page :text/html [req group-thing]
  (let [*lg*       (cfg/lib-grim-config)
        ?meta      (api/read-meta *lg* group-thing)
        ?artifacts (api/list-artifacts *lg* group-thing)
        ?notes     (api/list-notes *lg* group-thing)]
    (when (succeed? ?artifacts)
      (let [artifacts (result ?artifacts)
            meta      (when (succeed? ?meta) (result ?meta))]
        (layout
         (-> (cfg/site-config)
             (set-funding-flag req)
             (assoc :css ["/public/css/editable.css"]))
         ;;------------------------------------------------------------
         [:h1 {:class "page-title"}
          (header group-thing)]

         (or (when (succeed? ?notes)
               (when-let [note-thing (first (result ?notes))]
                 (let [note-text (result (api/read-note *lg* note-thing))]
                   (editable
                    "Group Notes"
                    (edit-url' note-thing)
                    (wutil/markdown-string note-text)))))

             (when-let [docs (:doc meta)]
               (addable
                "Group Docs"
                (add-note-url group-thing)
                [:pre "  " docs])))

         (list
          [:h2 "Known Artifacts"]
          [:ul (for [artifact (sort-by t/thing->name artifacts)
                     :let     [group (t/thing->group artifact)]]
                 [:li
                  [:a (link-to artifact)
                   (format "%s/%s"
                           (t/thing->name group)
                           (t/thing->name artifact))]])]))))))

(defmethod artifact-page :text/html [req artifact-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* artifact-thing)
        ?notes (api/list-notes *lg* artifact-thing)]
    (when (succeed? ?meta)
      (let [meta (result ?meta)]
        (layout
         (-> (cfg/site-config)
             (set-funding-flag req)
             (assoc :css ["/public/css/editable.css"]))
         ;;------------------------------------------------------------
         [:h1 {:class "page-title"}
          (header artifact-thing)]

         (or (when (succeed? ?notes)
               (when-let [note-thing (first (result ?notes))]
                 (let [note-text (result (api/read-note *lg* note-thing))]
                   (editable
                    "Arifact Notes"
                    (edit-url' note-thing)
                    (wutil/markdown-string note-text)))))

             (when-let [docs (:doc meta)]
               (addable
                "Artifact Docs"
                (add-note-url artifact-thing)
                [:pre "  " docs])))

         (list
          [:h2 "Known release versions"]
          [:ul (for [version (->> (api/list-versions *lg* artifact-thing)
                                  result
                                  (sort-by t/thing->name)
                                  reverse)
                     :let  [artifact (t/thing->artifact version)
                            group    (t/thing->group artifact)]]
                 [:li
                  [:a (link-to version)
                   (format "[%s/%s \"%s\"]"
                           (t/thing->name group)
                           (t/thing->name artifact)
                           (t/thing->name version))]])]))))))

(defmethod version-page :text/html [req version-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* version-thing)
        ?notes (api/list-notes *lg* version-thing)]
    (when (succeed? ?meta)
      (layout
       (-> (cfg/site-config)
           (set-funding-flag req)
           (assoc ;; FIXME: add artifact & group name to title somehow?
            :css ["/public/css/editable.css"]))
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"} (header version-thing)]

       (or (when (succeed? ?notes)
             (when-let [note-thing (first (result ?notes))]
               (let [note-text (result (api/read-note *lg* note-thing))]
                 (editable
                  "Release Notes"
                  (edit-url' note-thing)
                  (wutil/markdown-string note-text)))))

           (when-let [docs (:doc (result ?meta))]
             (addable
              "Version Docs"
              (add-note-url version-thing)
              [:pre "  " docs])))

       [:h2 "Platforms"]
       [:ul (for [platform-thing (->> (api/list-platforms *lg* version-thing)
                                      result
                                      (sort-by t/thing->name))]
              [:li [:a (link-to platform-thing)
                    (t/thing->name platform-thing)]])]))))

(defmethod platform-page :text/html [req platform-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* platform-thing)
        ?notes (api/list-notes *lg* platform-thing)]
    (when (succeed? ?meta)
      (layout
       (-> (cfg/site-config)
           (set-funding-flag req)
           (assoc  ;; FIXME: add artifact & group name to title somehow?
            :css ["/public/css/editable.css"]))
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"} (header platform-thing)]

       (or (when (succeed? ?notes)
             (when-let [note-thing (first (result ?notes))]
               (let [note-text (api/read-note *lg* note-thing)]
                 (editable
                  "Platform Notes"
                  (edit-url' note-thing)
                  (wutil/markdown-string note-text)))))

           (when-let [docs (:doc (result ?meta))]
             (addable
              "Official Documentation"
              (add-note-url platform-thing)
              [:pre "  " docs])))

       [:h2 "Namespaces"]
       [:ul (for [ns-thing (->> (api/list-namespaces *lg* platform-thing)
                                result (sort-by :name))]
              [:li [:a (link-to ns-thing)
                    (t/thing->name ns-thing)]])]))))

(defn emit-alphabetized-links [records]
  (let [segments (group-by (comp first str :name) records)]
    [:table
     (for [k (sort (keys segments))]
       [:tr
        [:td [:h4 (string/capitalize k)]]
        [:td (for [r (sort-by :name (get segments k))]
               [:a {:href (:url r) :style "padding: 0 0.2em;"}
                (:name r)])]])]))

(defmethod namespace-page :text/html [req namespace-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* namespace-thing)
        ?notes (api/list-notes *lg* namespace-thing)]
    (when (succeed? ?meta)
      (layout
       (-> (cfg/site-config)
           (set-funding-flag req)
           (assoc ;; FIXME: add artifact, namespace?
            :css ["/public/css/editable.css"]))
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"}
        (header namespace-thing)]

       (or (when (succeed? ?notes)
             (when-let [note-thing (first (result ?notes))]
               (let [note-text (-> *lg*
                                   (api/read-note note-thing)
                                   result)]
                 (editable
                  "Namespace Notes"
                  (edit-url' note-thing)
                  (wutil/markdown-string note-text)))))

           (let [{:keys [doc name]} (result ?meta)]
             (when doc
               (addable
                "Official Documentation"
                (add-note-url namespace-thing)
                [:pre "  " doc]))))

       (list [:h2 "Symbols"]
             ;; FIXME: the fuck am I doing here srsly
             (let [keys     [:special :macro :fn :var]
                   mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
                   ids      (zipmap keys ["sforms", "macros", "fns", "vars"])
                   link-ids (zipmap keys ["sff", "mf", "ff", "vf"])
                   grouping (->> (for [def-thing (-> (api/list-defs *lg* namespace-thing)
                                                     result)
                                       :let      [meta (-> *lg*
                                                           (api/read-meta def-thing)
                                                           result)]]
                                   {:url  (:href (link-to def-thing))
                                    :name (:name meta)
                                    :type (:type meta)})
                                 (group-by :type))]
               (for [k keys]
                 (when-let [records (get grouping k)]
                   (list
                    (let [links (emit-alphabetized-links records)
                          flag  (< 6 (count (second links)))]
                      [:div.section
                       [:h3.heading (get mapping k)
                        " " (if flag
                              [:span.unhide "+"]
                              [:span.hide "-"])]
                       [:div {:class (str "autofold"
                                          (when flag
                                            " prefold"))}
                        links]]))))))

       [:script {:src "/public/jquery.js" :type "text/javascript"}]
       [:script {:src "/public/fold.js" :type "text/javascript"}]))))

(defn -render-html-symbol-page [req def-thing meta]
  (let [*lg*                            (cfg/lib-grim-config)
        {:keys [src type arglists doc]} meta
        namespace                       (t/thing->name (t/thing->namespace def-thing))
        symbol                          (t/thing->name def-thing)
        ?related                        (api/list-related  *lg* def-thing) ;; Seq [ Thing [:def] ]
        ?examples                       (api/list-examples *lg* def-thing) ;; Seq [version, related]
        ?notes                          (api/list-notes    *lg* def-thing) ;; Seq [Note]
        *site-config*                   (cfg/site-config)]
    (layout
     (-> *site-config*
         (set-funding-flag req)
         (assoc :summary doc)
         (assoc :css ["/public/css/editable.css"
                      "/public/css/symbol.css"]))
     ;;------------------------------------------------------------
     [:h1 {:class "page-title"}
      (header (assoc def-thing :name (str symbol)))]

     (or (when (succeed? ?notes)
           (when-let [note-thing (first (result ?notes))]
             (let [note-text (result (api/read-note *lg* note-thing))]
               (editable
                "Community Documentation"
                (edit-url' note-thing)
                (wutil/markdown-string note-text)))))

         (when doc
           (addable
            "Official Documentation"
            (add-note-url def-thing)

            [:pre
             (when arglists
               (list (if (= type :special)
                       "Usage"
                       "Arities")
                     "\n==================================================\n"
                     (->> arglists
                          (map pr-str)
                          (map #(str "   " %))
                          (interpose \newline))))
             "\n\n"
             "Docstring\n"
             "==================================================\n"
             "  " doc])))

     ;; FIXME: examples needs a _lot_ of work
     (when (succeed? ?examples)
       [:div.section
        [:div {:class "clearfix"}
         [:h2.heading {:style "float:left;"}
          "Examples " [:span.hide "-"]]
         [:a {:style "float:right;"
              :href  (add-ex-url def-thing (str (rand-int Integer/MAX_VALUE) ".clj"))}
          "Add an example"]]
        [:div.autofold
         (for [[et n] (map vector (result ?examples) (range))]
           [:div.example
            (editable
             :h3
             (str "Example " (inc n))
             (edit-url' et)
             [:div.source
              (wutil/highlight-example et)])])]])

     (when-not (= :special type)
       [:a {:href (str "http://crossclj.info/fun/"
                       namespace "/" (wutil/url-encode symbol) ".html")}
        [:h2 "Uses on crossclj"]])

     (when (succeed? ?related)
       (let [results (result ?related)]
         (when-not (empty? results)
           (list [:h2 "Related Symbols"]
                 [:ul (for [r    results
                            :let [sym r
                                  ns  (t/thing->namespace sym)]]
                        [:a (link-to sym) (:name r)])]))))

     (when src
       (list
        [:div.section
         [:h2.heading "Source " [:span.unhide "+"]]
         [:div.autofold.prefold
          [:div.source
           (wutil/highlight-clojure src)]]]))

     [:script {:src "/public/jquery.js" :type "text/javascript"}]
     [:script {:src "/public/fold.js" :type "text/javascript"}])))

(defmethod symbol-page :text/html [req def-thing]
  (let [*lg*  (cfg/lib-grim-config)
        ?meta (api/read-meta *lg* def-thing)]
    (when (succeed? ?meta)
      (let [{:keys [type] :as meta} (result ?meta)]
        (cond (and meta (not= :sentinel type))
              ;; non-sentinel case
              ,,(-render-html-symbol-page req def-thing meta)

              (= :sentinel type)
              ;; chase a redirect
              ,,(when-let [target (:target meta)]
                  ;; FIXME: This limits the :target value to a fully
                  ;; qualified symbol rather than some other
                  ;; identifying structure like a full Thing. Whether
                  ;; this is a feature or not is open to debate but it
                  ;; works.
                  (let [[_ ns n] (re-find #"([^/]+)/(.+)" (str target))]
                    (recur :text/html
                           (-> (t/thing->platform def-thing)
                               (t/->Ns ns)
                               (t/->Def n)))))

              :else
              ;; fail to find a redirect, error out
              ,,nil)))))
