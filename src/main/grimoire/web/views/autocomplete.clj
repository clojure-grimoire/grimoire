(ns grimoire.web.views.autocomplete
  (:refer-clojure :exclude [ns-resolve])
  (:require [cheshire.core
             :refer [generate-string]]
            [grimoire
             [things :as t]
             [api :as api]
             [either :refer [result]]]
            [grimoire.api.web :as web]
            [grimoire.web
             [config :as cfg
              :refer [lib-grim-config
                      web-config]]
             [util :as u]]))

(def reader-shit
  [])

(def autocomplete-limit
  15)

(defn make-result [results]
  (if (empty? results)
    {:result :failure}
    {:result :success
     :body   results}))

(def get-nss
  (u/softref-cached
   (try
     (result
      (api/search (lib-grim-config)
                  [:ns nil nil :latest nil nil]))

     (catch Exception e
       nil))))

(def get-vars
  (u/softref-cached
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
                 (if-let [name (t/thing->name t)]
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
                 (let [name  (t/thing->name t)
                       pname (t/thing->name (t/thing->parent t))
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
