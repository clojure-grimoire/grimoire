(ns grimoire.web.views.autocomplete
  (:refer-clojure :exclude [ns-resolve])
  (:require [cheshire.core :refer [generate-string]]
            [grimoire
             [api :as api]
             [either :refer [result]]
             [things :as t]]
            [grimoire.api.web :as web]
            [grimoire.web
             [config :as cfg :refer [lib-grim-config web-config]]
             [util :as u]]))

(def reader-shit
  (let [prefix          "http://clojure.org/reference/reader#"
        url             (fn [postfix] (str prefix postfix))
        dispatch-url    (url "_dispatch")
        syntax-url      (url "__a_id_syntax_quote_a_syntax_quote_note_the_backquote_character_unquote_and_unquote_splicing")
        reader-cond-url (url "_reader_conditionals")]
    [{:label "[" :url (url "_vectors")}
     {:label "{" :url (url "_lists")}
     {:label "'" :url (url "_quote")}
     {:label "\\" :url (url "_character")}
     {:label ";" :url (url "_comment")}
     {:label "@" :url (url "_deref")}
     {:label "^" :url (url "_metadata")}

     {:label "`" :url syntax-url}
     {:label "~" :url syntax-url}
     {:label "~@" :url syntax-url}

     {:label "#?" :url reader-cond-url}
     {:label "#?@" :url reader-cond-url}
     
     {:label "#" :url dispatch-url}
     {:label "#\"" :url dispatch-url}
     {:label "#'" :url dispatch-url}
     {:label "#(" :url dispatch-url}
     
     {:label "#{" :url "http://clojure.org/reference/reader#_sets"}]))

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

(defn empty->nil [coll]
  (when-not (empty? coll) coll))

(defn complete-reader [qstr]
  (->> reader-shit
       (keep (fn [{:keys [label url] :as res}]
               (when (.startsWith label qstr)
                 res)))
       (take autocomplete-limit)
       empty->nil))

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
           ,,(or (complete-reader qstr)
                 (complete-vars qstr))

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
