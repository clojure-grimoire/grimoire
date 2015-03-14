(ns grimoire.web.views.content.html
  (:require [grimoire.web.views :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.either :refer [succeed? result]]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]
            [grimoire.web.config :as cfg]
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

(defn heatmap-page []
  (layout
   (cfg/site-config)
   ;;------------------------------------------------------------
   [:h1 {:class "page-title"} "Analytics!"]
   [:p "Or at least some of it >:D"]
   (let [service @cfg/service
         db      (-> service :simpledb :db deref)]
     (list [:div
            [:h2 "Top 100 namespaces"]
            (kv-table (take 100 (reverse (sort-by second (:namespaces db)))))]

           [:div
            [:h2 "Top 100 defs"]
            (kv-table (take 100 (reverse (sort-by second (:defs db)))))]

           [:div
            [:h2 "Top artifacts"]
            (kv-table (take 100 (reverse (sort-by second (:artifacts db)))))]

           [:div
            [:h2 "Top platforms"]
            (kv-table (reverse (sort-by second (:platforms db))))]

           [:div
            [:h2 "Top clients"]
            (kv-table (reverse (sort-by second (:clients db))))]))))

(defmethod store-page :text/html [_]
  (let [groups (api/list-groups (cfg/lib-grim-config) result)]
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
  (let [?artifacts (api/list-artifacts (cfg/lib-grim-config) group-thing)]
    (when (succeed? ?artifacts)
      (let [artifacts (result ?artifacts)]
        (layout
         (cfg/site-config)
         ;;------------------------------------------------------------
         [:h1 {:class "page-title"}
          (header group-thing)]

         (let [?notes (api/list-notes (cfg/lib-grim-config) group-thing)]
           (when (succeed? ?notes)
             (let [notes (result ?notes)]
               (when notes
                 (list
                  [:h2 "Group Notes"]
                  (wutil/markdown-string notes))))))

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
  (let [?meta (api/read-meta (cfg/lib-grim-config) artifact-thing)]
    (when (succeed? ?meta)
      (layout
       (cfg/site-config)
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"}
        (header artifact-thing)]

       (let [?notes (api/list-notes (cfg/lib-grim-config) artifact-thing)]
         (when (succeed? ?notes)
           (let [note (result (api/read-note (cfg/lib-grim-config) (first (result ?notes))))]
             (list
              [:h2 "Arifact Notes"]
              (wutil/markdown-string note)))))

       (list
        [:h2 "Known release versions"]
        [:ul (for [version (->> (api/list-versions (cfg/lib-grim-config) artifact-thing)
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
                         (t/thing->name version))]])])))))

(defmethod version-page :text/html [_ version-thing]
  (let [?meta  (api/read-meta (cfg/lib-grim-config) version-thing)
        ?notes (api/read-notes (cfg/lib-grim-config) version-thing)]
    (when (succeed? ?meta)
      (layout
       (cfg/site-config) ;; FIXME: add artifact & group name to title somehow?
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"} (header version-thing)]
       (when (succeed? ?notes)
         (let [notes (result ?notes)]
           (println notes)
           (when-not (empty? notes)
             (list
              [:h2 "Release Notes"]
              (wutil/markdown-string
               (-> notes first second))))))

       [:h2 "Platforms"]
       [:ul (for [platform-thing (->> (api/list-platforms (cfg/lib-grim-config) version-thing)
                                      result
                                      (sort-by t/thing->name))]
              [:li [:a (link-to' platform-thing)
                    (t/thing->name platform-thing)]])]))))

(defmethod platform-page :text/html [_ platform-thing]
  (let [?meta  (api/read-meta (cfg/lib-grim-config) platform-thing)
        ?notes (api/read-notes (cfg/lib-grim-config) platform-thing)]
    (when (succeed? ?meta)
      (layout
       (cfg/site-config) ;; FIXME: add artifact & group name to title somehow?
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"} (header platform-thing)]
       (when (succeed? ?notes)
         (when-let [note (second (first (result ?notes)))]
           (list [:h2 "Platform Notes"]
                 (wutil/markdown-string note))))

       [:h2 "Namespaces"]
       [:ul (for [ns-thing (->> (api/list-namespaces (cfg/lib-grim-config) platform-thing)
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
  (let [?meta  (api/read-meta (cfg/lib-grim-config) namespace-thing)
        ?notes (api/read-notes (cfg/lib-grim-config) namespace-thing)]
    (when (succeed? ?meta)
      (layout
       (cfg/site-config) ;; FIXME: add artifact, namespace?
       ;;------------------------------------------------------------
       [:h1 {:class "page-title"}
        (header namespace-thing)]

       (let [{:keys [doc name]} meta]
         (when doc
           (list [:h2 "Namespace Docs"]
                 [:p doc])))

       (when (succeed? ?notes)
         (when-let [notes (second (first (result ?notes)))]
           (list [:h2 "Namespace Notes"]
                 [:p (wutil/markdown-string notes)])))

       (list [:h2 "Symbols"]
             ;; FIXME: the fuck am I doing here srsly
             (let [keys     [:special :macro :fn :var]
                   mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
                   ids      (zipmap keys ["sforms", "macros", "fns", "vars"])
                   link-ids (zipmap keys ["sff", "mf", "ff", "vf"])
                   grouping (->> (for [def-thing (-> (api/list-defs (cfg/lib-grim-config) namespace-thing)
                                                     result)
                                       :let      [meta (-> (cfg/lib-grim-config)
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
  (let [{:keys [src type arglists doc name]} meta
        namespace (t/thing->name (t/thing->namespace def-thing))
        symbol    name
        ?related  (api/list-related  (cfg/lib-grim-config) def-thing) ;; Seq [ Thing [:def] ]
        ?examples (api/list-examples (cfg/lib-grim-config) def-thing) ;; Seq [version, related]
        ?notes    (api/read-notes    (cfg/lib-grim-config) def-thing)]
    (layout
     (assoc (cfg/site-config)
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
             [:pre "  " doc]))

     (when (succeed? ?notes)
       (let [[[_ notes]] (result ?notes)]
         (when notes
           (list [:h2 "Community Documentation"]
                 ;; FIXME: Add edit URL!
                 (wutil/markdown-string notes)))))

     ;; FIXME: examples needs a _lot_ of work
     (when (succeed? ?examples)
       [:div.section
        [:h2.heading "Examples " [:span.hide "-"]]
        [:div.autofold
         (for [et   (result ?examples)
               :let [e (result (api/read-example (cfg/lib-grim-config) et))]]
           [:div.example
            [:div.source
             (wutil/highlight-clojure e)]])]])

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
  (let [?meta (api/read-meta (cfg/lib-grim-config) def-thing)]
    (when (succeed? ?meta)
      (let [{:keys [type] :as meta} (result ?meta)]
        (cond (and meta (not= :sentinel type))
              ;; non-sentinel case
              ,,(-render-html-symbol-page def-thing meta)

              (= :sentinel type)
              ;; chase a redirect
              ,,(when-let [target (:target meta)]
                  (symbol-page :text/html
                               (t/path->thing
                                (str (t/thing->path (t/thing->version def-thing))
                                     "/" target))))

              :else
              ;; fail to find a redirect, error out
              ,,nil)))))
