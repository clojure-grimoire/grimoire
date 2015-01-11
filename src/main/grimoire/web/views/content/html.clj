(ns grimoire.web.views.content.html
  (:require [grimoire.web.views :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.either :refer [succeed? result]]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]))

(defmethod store-page :text/html [_]
  (try
    (let [groups (-> site-config api/list-groups result)]
      (layout site-config
              [:h1 {:class "page-title"}
               ,,"Artifact store"]
              (list
               [:h2 "Known Maven groups"]
               [:ul (for [group (sort-by :name groups)]
                      [:li [:a (link-to' group)
                            (:name group)]])])))

    ;; FIXME: more specific error
    (catch AssertionError e
      nil)))

(defmethod group-page :text/html [_ group-thing]
  (try
    (let [artifacts (-> site-config
                        (api/list-artifacts group-thing)
                        result)]
      (layout site-config
              [:h1 {:class "page-title"}
               (header group-thing)]

              (let [?notes (api/read-notes site-config group-thing)]
                (when (succeed? ?notes)
                  (let [[[_ notes]] (result ?notes)]
                    (when notes
                      (list
                       [:h2 "Group Notes"]
                       (wutil/markdown-string notes))))))

              (list
               [:h2 "Known Artifacts"]
               [:ul (for [artifact (sort-by :name artifacts)
                          :let     [group (:parent artifact)]]
                      [:li
                       [:a (link-to' artifact)
                        (format "%s/%s"
                                (:name group)
                                (:name artifact))]])])))

    ;; FIXME: more specific error
    (catch AssertionError e
      (println (.getMessage e))
      nil)))

(defmethod artifact-page :text/html [_ artifact-thing]
  (try
    (layout site-config
            [:h1 {:class "page-title"}
             (header artifact-thing)]

            (let [?notes-seq (api/read-notes site-config artifact-thing)]
              (when (succeed? ?notes-seq)
                (let [[[_ notes]] (result ?notes-seq)]
                  (when notes
                    (list
                     [:h2 "Arifact Notes"]
                     (wutil/markdown-string notes))))))

            (list
             [:h2 "Known release versions"]
             [:ul (for [version (->> (api/list-versions site-config artifact-thing)
                                     result (sort-by :name) reverse)
                        :let  [artifact (:parent version)
                               group    (:parent artifact)]]
                    [:li
                     [:a (link-to' version)
                      (format "[%s/%s \"%s\"]"
                              (:name group)
                              (:name artifact)
                              (:name version))]])]))

    ;; FIXME: more specific error
    (catch AssertionError e
      nil)))

(defmethod version-page :text/html [_ version-thing]
  (try
    (let [?notes (api/read-notes site-config version-thing)]
      (layout site-config ;; FIXME: add artifact & group name to title somehow?
              [:h1 {:class "page-title"} (header version-thing)]
              (when (succeed? ?notes)
                (list [:h2 "Release Notes"]
                      (wutil/markdown-string (-> ?notes result first second))))

              [:h2 "Platforms"]
              [:ul (for [platform-thing (->> (api/list-platforms site-config version-thing)
                                             result (sort-by :name))]
                     [:li [:a (link-to' platform-thing)
                           (:name platform-thing)]])]))

    ;; FIXME: more specific error type
    (catch AssertionError e
      nil)))

(defmethod platform-page :text/html [_ platform-thing]
  (try
    (let [?notes (api/read-notes site-config platform-thing)]
      (layout site-config ;; FIXME: add artifact & group name to title somehow?
              [:h1 {:class "page-title"} (header platform-thing)]
              (when (succeed? ?notes)
                (list [:h2 "Platform Notes"]
                      (wutil/markdown-string (-> ?notes result first second))))

              [:h2 "Namespaces"]
              [:ul (for [ns-thing (->> (api/list-namespaces site-config platform-thing)
                                       result (sort-by :name))]
                     [:li [:a (link-to' ns-thing)
                           (:name ns-thing)]])]))
    ;; FIXME: more specific error type
    (catch AssertionError e
      nil)))

(defn emit-alphabetized-links [records]
  (let [segments (group-by (comp first str :name) records)]
    (for [k (sort (keys segments))]
      (list [:h4 (string/capitalize k)]
            [:p (for [r (sort-by :name (get segments k))]
                  [:a {:href (:url r) :style "padding: 0 0.2em;"} (:name r)])]))))

(defmethod namespace-page :text/html [_ namespace-thing]
  (try
    (let [meta   (result (api/read-meta site-config namespace-thing))
      ?notes (api/read-notes site-config namespace-thing)]
    (layout site-config ;; FIXME: add artifact, namespace?
            [:h1 {:class "page-title"}
             (header namespace-thing)]

            (let [{:keys [doc name]} meta]
              (when doc
                (list [:h2 "Namespace Docs"]
                      [:p doc])))

            (when (succeed? ?notes)
              (let [[[_ notes]] (result ?notes)]
                (when notes
                  (list [:h2 "Namespace Notes"]
                        [:p (wutil/markdown-string notes)]))))

            (list [:h2 "Symbols"]
                  ;; FIXME: the fuck am I doing here srsly
                  (let [keys     [:special :macro :fn :var]
                        mapping  (zipmap keys ["Special Forms", "Macros", "Functions", "Vars"])
                        ids      (zipmap keys ["sforms", "macros", "fns", "vars"])
                        link-ids (zipmap keys ["sff", "mf", "ff", "vf"])
                        grouping (->> (for [def-thing (-> site-config
                                                          (api/list-defs namespace-thing)
                                                          result)
                                            :let      [meta (-> site-config
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
            [:script {:src "/public/fold.js" :type "text/javascript"}]))

  ;; FIXME: more specific error type
  (catch AssertionError e
    nil)))

(defn -render-html-symbol-page [def-thing meta]
  (let [{:keys [src type arglists doc name]} meta
        namespace (:name (t/thing->namespace def-thing))
        symbol    name
        ?related   (api/read-related site-config def-thing) ;; Seq [ Thing [:def] ]
        ?examples (api/read-examples site-config def-thing) ;; Seq [version, related]
        ?notes    (api/read-notes    site-config def-thing)]
    (layout (assoc site-config
                   :page {:description (str namespace "/" symbol
                                            " - documentation and examples")
                          :summary doc})

            [:h1 {:class "page-title"}
             (header (assoc def-thing :name name))]

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
                (for [[v e] (result ?examples)]
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
                                         ns  (:parent sym)]]
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
  (let [?meta (api/read-meta site-config def-thing)]
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
                                (str (:uri (t/thing->version def-thing))
                                     "/" target))))

              :else
              ;; fail to find a redirect, error out
              ,,nil)))))
