(ns grimoire.web.views.autocomplete
  (:refer-clojure :exclude [ns-resolve])
  (:require [grimoire.api :as api]
            [grimoire.api.web :as web]
            [grimoire.either
             :refer [succeed? result]]
            [grimoire.web.views
             :refer [ns-version-index]]
            [grimoire.web.config
             :refer [lib-grim-config
                     site-config
                     web-config]]
            [grimoire.things :as t]
            [cheshire.core
             :refer [generate-string]]
            [grimoire.web.config :as cfg])
  (:import [java.lang.ref SoftReference]))

(def reader-shit
  [])

(def autocomplete-limit
  15)

(defn make-result [results]
  (if (empty? results)
    {:result :failure}
    {:result :success
     :body   results}))

(defmacro softref-cached [& body]
  `(let [cache# (atom (SoftReference. nil))]
     (fn []
       (or (.get ^SoftReference @cache#)
           (let [res# (do ~@body)]
             (reset! cache# (SoftReference. res#))
             res#)))))

(def get-nss
  (softref-cached
   (try
     (result
      (api/search (lib-grim-config)
                  [:ns nil nil :latest nil nil]))

     (catch Exception e
       nil))))

(def get-vars
  (softref-cached
   (try
     (result
      (api/search (lib-grim-config)
                  [:def nil nil :latest nil nil nil]))
     (catch Exception e
       nil))))

(defn complete-nss [qstr]
  (let [*cfg* (web-config)
        db    (-> @cfg/service :simpledb :db deref)
        nss   (:namespaces db)]
    (->> (get-nss)
         (keep (fn [t]
                 (if-let [name (:name t)]
                   (if (.startsWith name qstr)
                     {:label name
                      :url   (web/make-html-url *cfg* t)
                      :e     (str "clj::" name)}))))
         (sort-by #(get (:e %) nss 0))
         (reverse)
         (take autocomplete-limit))))

(defn complete-vars [qstr]
  (let [*cfg* (web-config)
        db    (-> @cfg/service :simpledb :db deref)
        defs  (:defs db)]
    (->> (get-vars)
         (keep (fn [t]
                 (let [name  (:name t)
                       pname (:name (:parent t))
                       fname (str pname "/" name)]
                   (if (and name
                            (or (.startsWith name qstr)
                                (.startsWith fname qstr)))
                     {:label fname
                      :url   (web/make-html-url *cfg* t)
                      :fname fname}))))
         (sort-by #(get (:fname %) defs 0))
         (reverse)
         (take autocomplete-limit))))

(defn complete [qstr]
  (println "Completing " qstr)
  (let [*cfg* (web-config)]
    (->> (cond
           ;; FIXME: links for reader notation
           (re-find #"^\W" qstr)
           ,,nil

           (re-find #"[:]?([\\D&&[^/]].*/)?(/|[\\D&&[^/]][^/]*)" qstr)
           ,,(complete-vars qstr)
           
           ;; Links for namespaces (one . and no / yet)
           (re-find #"^[^A-Z.\W]*?\.[^/]+" qstr)
           ,,(complete-nss qstr)

           :else
           ,,(or (complete-vars qstr)
                 (complete-nss qstr)))
         make-result
         generate-string)))
