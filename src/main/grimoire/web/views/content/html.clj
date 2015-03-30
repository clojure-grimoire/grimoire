(ns grimoire.web.views.content.html
  (:require [grimoire.web.views :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.either :refer [succeed? result]]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]
            [grimoire.web.config :as cfg]
            [grimoire.github :as gh]
            [simpledb.core :as sdb]))

;; FIXME: probably belongs somewhere else
(defn home-page []
  (layout
   (cfg/site-config)
   ;;------------------------------------------------------------
   [:blockquote [:p (-> (cfg/site-config) :style :quote)]]
   (wutil/cheatsheet-memo (cfg/site-config))))

(defn kv-table [kv-seq]
  [:table
   (for [[k v] kv-seq]
     [:tr
      [:td {:style "width: 90%;"} k]
      [:td {:style "width: 10%;"} v]])])

(defn editable
  ([title link content]
   (editable :h2 title link content))

  ([title-size title link content]
   [:div.editable
    [:span.header
     (when title [title-size title])
     [:a.edit {:href link} "Edit"]]
    content]))

(defn edit-url' [t]
  {:pre [(or (t/note? t)
             (t/example? t))]}
  (gh/->edit-url (cfg/notes-config) "develop" (::t/file t)))

;; FIXME: this entire fuction is too datastore-aware by a lot
(defn heatmap-page []
  (layout
   (cfg/site-config)
   ;;------------------------------------------------------------
   [:h1 {:class "page-title"} "Analytics!"]
   [:p "Or at least some of it >:D"]
   (let [service @cfg/service
         db      (-> service :simpledb :db deref)
         sorted-table #(->> % (sort-by second) reverse (take 100) kv-table)]
     (list [:div
            [:h2 "Top 100 namespaces"]
            (->> db :namespaces sorted-table)]

           [:div
            [:h2 "Top 100 defs"]
            (->> db :defs sorted-table)]

           [:div
            [:h2 "Top artifacts"]
            (->> db :artifacts sorted-table)]

           [:div
            [:h2 "Top platforms"]
            (->> db :platforms sorted-table)]

           [:div
            [:h2 "Top clients"]
            (->> db :clients sorted-table)]))))

(defmethod store-page :text/html [_]
  (let [*lg*   (cfg/lib-grim-config)
        groups (result (api/list-groups *lg*))]
    (layout
     (cfg/site-config)
     ;;------------------------------------------------------------
     [:h1 {:class "page-title"}
      ,,"Artifact store"]
     (list
      [:h2 "Known Maven groups"]
      [:ul (for [group (sort-by t/thing->name groups)]
             [:li [:a (link-to' group)
                   (t/thing->name group)]])]))))

(defmethod group-page :text/html [_ group-thing]
  (let [*lg*       (cfg/lib-grim-config)
        ?meta      (api/read-meta *lg* group-thing)
        ?artifacts (api/list-artifacts *lg* group-thing)
        ?notes     (api/list-notes *lg* group-thing)]
    (when (succeed? ?artifacts)
      (let [artifacts (result ?artifacts)
            meta      (when (succeed? ?meta) (result ?meta))]
        (layout
         (cfg/site-config)
         ;;------------------------------------------------------------
         [:h1 {:class "page-title"}
          (header group-thing)]

         (when-let [docs (:doc meta)]
           (list
            [:h2 "Group Docs"]
            [:pre "  " docs]))

         (when (succeed? ?notes)
           (when-let [note-thing (first (result ?notes))]
             (let [note-text (result (api/read-note *lg* note-thing))]
               (editable
                "Group Notes"
                (edit-url' note-thing)
                (wutil/markdown-string note-text)))))

         (list
          [:h2 "Known Artifacts"]
          [:ul (for [artifact (sort-by t/thing->name artifacts)
                     :let     [group (t/thing->group artifact)]]
                 [:li
                  [:a (link-to' artifact)
                   (format "%s/%s"
                           (t/thing->name group)
                           (t/thing->name artifact))]])]))))))

(defmethod artifact-page :text/html [_ artifact-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* artifact-thing)
        ?notes (api/list-notes *lg* artifact-thing)]
    (when (succeed? ?meta)
      (let [meta (result ?meta)]
        (layout
         (cfg/site-config)
         ;;------------------------------------------------------------
         [:h1 {:class "page-title"}
          (header artifact-thing)]

         (when-let [docs (:doc meta)]
           (list
            [:h2 "Artifact Docs"]
            [:pre "  " docs]))

         (when (succeed? ?notes)
           (when-let [note-thing (first (result ?notes))]
             (let [note-text (result (api/read-note *lg* note-thing))]
               (editable
                "Arifact Notes"
                (edit-url' note-thing)
                (wutil/markdown-string note-text)))))

         (list
          [:h2 "Known release versions"]
          [:ul (for [version (->> (api/list-versions *lg* artifact-thing)
                                  result
                                  (sort-by t/thing->name)
                                  reverse)
                     :let  [artifact (t/thing->artifact version)
                            group    (t/thing->group artifact)]]
                 [:li
                  [:a (link-to' version)
                   (format "[%s/%s \"%s\"]"
                           (t/thing->name group)
                           (t/thing->name artifact)
                           (t/thing->name version))]])]))))))

(defmethod version-page :text/html [_ version-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* version-thing)
        ?notes (api/list-notes *lg* version-thing)]
    (when (succeed? ?meta)
      (layout
       (cfg/site-config) ;; FIXME: add artifact & group name to title somehow?
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"} (header version-thing)]

       (when-let [docs (:doc (result ?meta))]
         (list
          [:h2 "Version Docs"]
          [:pre "  " docs]))

       (when (succeed? ?notes)
         (when-let [note-thing (first (result ?notes))]
           (let [note-text (result (api/read-note *lg* note-thing))]
             (editable
              "Release Notes"
              (edit-url' note-thing)
              (wutil/markdown-string note-text)))))

       [:h2 "Platforms"]
       [:ul (for [platform-thing (->> (api/list-platforms *lg* version-thing)
                                      result
                                      (sort-by t/thing->name))]
              [:li [:a (link-to' platform-thing)
                    (t/thing->name platform-thing)]])]))))

(defmethod platform-page :text/html [_ platform-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* platform-thing)
        ?notes (api/list-notes *lg* platform-thing)]
    (when (succeed? ?meta)
      (layout
       (cfg/site-config) ;; FIXME: add artifact & group name to title somehow?
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"} (header platform-thing)]

       (when-let [docs (:doc (result ?meta))]
         (list
          [:h2 "Platform Docs"]
          [:pre "  " docs]))

       (when (succeed? ?notes)
         (when-let [note-thing (first (result ?notes))]
           (let [note-text (api/read-note *lg* note-thing)]
             (editable
              "Platform Notes"
              (edit-url' note-thing)
              (wutil/markdown-string note-text)))))

       [:h2 "Namespaces"]
       [:ul (for [ns-thing (->> (api/list-namespaces *lg* platform-thing)
                                result (sort-by :name))]
              [:li [:a (link-to' ns-thing)
                    (t/thing->name ns-thing)]])]))))

(defn emit-alphabetized-links [records]
  (let [segments (group-by (comp first str :name) records)]
    (for [k (sort (keys segments))]
      (list [:h4 (string/capitalize k)]
            [:p (for [r (sort-by :name (get segments k))]
                  [:a {:href (:url r) :style "padding: 0 0.2em;"}
                   (:name r)])]))))

(defmethod namespace-page :text/html [_ namespace-thing]
  (let [*lg*   (cfg/lib-grim-config)
        ?meta  (api/read-meta *lg* namespace-thing)
        ?notes (api/list-notes *lg* namespace-thing)]
    (when (succeed? ?meta)
      (layout
       (cfg/site-config) ;; FIXME: add artifact, namespace?
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"}
        (header namespace-thing)]

       (let [{:keys [doc name]} (result ?meta)]
         (when doc
           (list
            [:h2 "Namespace Docs"]
            [:pre "  " doc])))

       (when (succeed? ?notes)
         (when-let [note-thing (first (result ?notes))]
           (let [note-text (-> *lg*
                               (api/read-note note-thing)
                               result)]
             (editable
              "Namespace Notes"
              (edit-url' note-thing)
              (wutil/markdown-string note-text)))))

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
       [:script {:src "/public/fold.js" :type "text/javascript"}]))))

(defn -render-html-symbol-page [def-thing meta]
  (let [*lg*                                 (cfg/lib-grim-config)
        {:keys [src type arglists doc name]} meta
        namespace                            (t/thing->name (t/thing->namespace def-thing))
        symbol                               name
        ?related                             (api/list-related  *lg* def-thing) ;; Seq [ Thing [:def] ]
        ?examples                            (api/list-examples *lg* def-thing) ;; Seq [version, related]
        ?notes                               (api/list-notes    *lg* def-thing) ;; Seq [Note]
        -site-config                         (cfg/site-config)]
    (layout
     (assoc -site-config
            :page {:description (str namespace "/" symbol
                                     " - documentation and examples")
                   :summary doc})
     ;;------------------------------------------------------------
     [:h1 {:class "page-title"}
      (header (assoc def-thing :name (str symbol)))]

     (when arglists
       (list [:h2 (if (= type :special)
                    "Usage"
                    "Arities")]
             [:pre (->> arglists
                        (map pr-str)
                        (map #(str "   " %))
                        (interpose \newline))]))

     (when doc
       (list [:h2 "Official Documentation"]
             doc))

     (when (succeed? ?notes)
       (when-let [note-thing (first (result ?notes))]
         (let [note-text (result (api/read-note *lg* note))]
           (editable
            "Community Documentation"
            (edit-url' note-thing)
            (wutil/markdown-string note-text)))))

     ;; FIXME: examples needs a _lot_ of work
     (when (succeed? ?examples)
       [:div.section
        [:h2.heading "Examples " [:span.hide "-"]]
        [:div.autofold
         (for [et   (result ?examples)
               :let [e (result (api/read-example *lg* et))]]
           [:div.example
            (editable
             nil
             (edit-url' et)
             [:div.source
              (wutil/highlight-clojure e)])])]])

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
                        [:a (link-to' sym) (:name r)])]))))

     (when src
       (list
        [:div.section
         [:h2.heading "Source " [:span.unhide "+"]]
         [:div.autofold.prefold
          (wutil/highlight-clojure src)]]))

     [:script {:src "/public/jquery.js" :type "text/javascript"}]
     [:script {:src "/public/fold.js" :type "text/javascript"}])))

(defmethod symbol-page :text/html [_ def-thing]
  (let [*lg*  (cfg/lib-grim-config)
        ?meta (api/read-meta *lg* def-thing)]
    (when (succeed? ?meta)
      (let [{:keys [type] :as meta} (result ?meta)]
        (cond (and meta (not= :sentinel type))
              ;; non-sentinel case
              ,,(-render-html-symbol-page def-thing meta)

              (= :sentinel type)
              ;; chase a redirect
              ,,(when-let [target (:target meta)]
                  ;; FIXME: This limits the :target value to a fully
                  ;; qualified symbol rather than some other
                  ;; identifying structure like a full Thing. Whether
                  ;; this is a feature or not is open to debate but it
                  ;; works.
                  (let [[_ ns n] (re-find #"([^/]+)/(.+)" (str target))]
                    (symbol-page :text/html
                                 (-> (t/thing->platform def-thing)
                                     (t/->Ns ns)
                                     (t/->Def n)))))

              :else
              ;; fail to find a redirect, error out
              ,,nil)))))
