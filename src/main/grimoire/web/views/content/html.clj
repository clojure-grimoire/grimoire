(ns grimoire.web.views.content.html
  (:require [grimoire.web.views :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]))

(defmethod store-page :text/html [_]
  (try
    (layout site-config
            [:h1 {:class "page-title"}
             ,,"Artifact store"]
            [:h2 "Known Maven groups"]
            [:ul (for [group (->> (api/list-groups site-config)
                                (sort-by :name))]
                   [:li
                    [:a (link-to' group)
                     (:name group)]])])

    ;; FIXME: more specific error
    (catch Exception e
      nil)))

(defmethod group-page :text/html [_ group-thing]
  (try
    (layout site-config
            [:h1 {:class "page-title"} (header group-thing)]
            [:h2 "Known artifacts"]
            [:ul (for [artifact (->> (api/list-artifacts site-config group-thing)
                                   (sort-by :name))
                       :let  [group (:parent artifact)]]
                   [:li
                    [:a (link-to' artifact)
                     (format "%s/%s"
                             (:name group)
                             (:name artifact))]])])

    ;; FIXME: more specific error
    (catch Exception e)))

(defmethod artifact-page :text/html [_ artifact-thing]
  (try
    (layout site-config
            [:h1 {:class "page-title"}
             (header artifact-thing)]
            (let [notes-seq          (api/read-notes site-config artifact-thing)
                  [[_ latest-notes]] notes-seq]
              (when latest-notes
                (list [:h2 "Arifact Notes"]
                      (wutil/markdown-string latest-notes))))

            [:h2 "Known release versions"]
            [:ul (for [version (->> (api/list-versions site-config artifact-thing)
                                  (sort-by :name)
                                  reverse)
                       :let  [artifact (:parent version)
                              group    (:parent artifact)]]
                   [:li
                    [:a (link-to' version)
                     (format "[%s/%s \"%s\"]"
                             (:name group)
                             (:name artifact)
                             (:name version))]])])

    ;; FIXME: more specific error
    (catch Exception e
      nil)))

(defmethod version-page :text/html [_ version-thing]
  (try
    (let [[[v notes]] (api/read-notes site-config version-thing)]
      (layout site-config ;; FIXME: add artifact & group name to title somehow?
              [:h1 {:class "page-title"} (header version-thing)]
              (when notes
                (list [:h2 "Release Notes"]
                      (wutil/markdown-string notes)))

              [:h2 "Namespaces"]
              [:ul
               (for [ns-thing (->> (api/list-namespaces site-config version-thing)
                                 (sort-by :name))]
                 [:li
                  [:a (link-to' ns-thing) (:name ns-thing)]])]))

    ;; FIXME: more specific error type
    (catch Exception e
      nil)))

(defn emit-alphabetized-links [records]
  (let [segments (group-by (comp first str :name) records)]
    (for [k (sort (keys segments))]
      (list [:h4 (string/capitalize k)]
            [:p (for [r (sort-by :name (get segments k))]
                  [:a {:href (:url r) :style "padding: 0 0.2em;"} (:name r)])]))))

(defmethod namespace-page :text/html [_ namespace-thing]
  (try
    (let [meta (api/read-meta site-config namespace-thing)]
      (layout site-config ;; FIXME: add artifact, namespace?
              [:h1 {:class "page-title"}
               (header namespace-thing)]

              (let [{:keys [doc name]} meta]
                (when doc
                  (list [:h2 "Namespace Docs"]
                        [:p doc])))

              (let [[[_ notes]] (api/read-notes site-config namespace-thing)]
                (when notes
                  (list [:h2 "Namespace Notes"]
                        [:p (wutil/markdown-string notes)])))

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

    ;; FIXME: more specific error type
    (catch Exception e
      nil)))

(defn -render-html-symbol-page [def-thing meta]
  (let [{:keys [src type arglists doc name]} meta
        namespace (:name (t/thing->namespace def-thing))
        symbol    name
        related   (api/read-related site-config def-thing)    ;; Seq [ Thing [:def] ]
        examples  (api/read-examples site-config def-thing)]  ;; Seq [version, related]
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

            (let [[[_ notes]] (api/read-notes site-config def-thing)]
              (when notes
                (list [:h2 "Community Documentation"]
                      ;; FIXME: Add edit URL!
                      (wutil/markdown-string notes))))

            ;; FIXME: examples needs a _lot_ of work
            (when-not (empty? examples)
              [:div.section
               [:h2.heading "Examples " [:span.hide "-"]]
               [:div.autofold
                (for [[v e] examples]
                  [:div.example
                   [:div.source
                    (wutil/highlight-clojure e)]])]])

            (when-not (= :special type)
              [:a {:href (str "http://crossclj.info/fun/"
                              namespace "/" (wutil/url-encode symbol) ".html")}
               [:h2 "Uses on crossclj"]])

            (when-not (empty? related)
              (list [:h2 "Related Symbols"]
                    [:ul (for [r    related
                               :let [sym r
                                     ns (:parent sym)]]
                           [:a (link-to' sym) (:name r)])]))

            (when src
              (list
               [:div.section
                [:h2.heading "Source " [:span.unhide "+"]]
                [:div.autofold.prefold
                 (wutil/highlight-clojure src)]]))

            [:script {:src "/public/jquery.js" :type "text/javascript"}]
            [:script {:src "/public/fold.js" :type "text/javascript"}])))

(defmethod symbol-page :text/html [_ def-thing]
  (let [{:keys [type] :as meta} (api/read-meta site-config def-thing)]
    (cond (and meta
             (not (= :sentinel type)))
          ,,(-render-html-symbol-page def-thing meta)

          ;; chase a redirect
          (= :sentinel type)
          ,,(when-let [target (:target meta)]
              (symbol-page :text/html
                           (t/path->thing
                            (str (:uri (t/thing->version def-thing))
                                 "/" target))))

          :else
          ;; fail to find a redirect, error out
          ,,nil)))
