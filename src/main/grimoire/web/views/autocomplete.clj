(ns grimoire.web.views.autocomplete
  (:refer-clojure :exclude [ns-resolve])
  (:require [cheshire.core :refer [generate-string]]
            [clj-fuzzy.levenshtein :rename {distance levenshtein}]
            [grimoire
             [api :as api]
             [either :refer [result]]
             [things :as t]]
            [grimoire.api.web :as web]
            [grimoire.web
             [config :as cfg :refer [lib-grim-config web-config]]
             [util :as u]]
            [taoensso.timbre :refer [info]]))

(def reader-shit
  (let [prefix          "http://clojure.org/reference/reader#"
        url             (fn [postfix] (str prefix postfix))
        dispatch-url    (url "_dispatch")
        syntax-url      (url "__a_id_syntax_quote_a_syntax_quote_note_the_backquote_character_unquote_and_unquote_splicing")
        reader-cond-url (url "_reader_conditionals")]
    [
     {:label "[ ... ] - Vector literals" :url (url "_vectors")}
     {:label "{ ... } - Map literals" :url (url "_lists")}
     {:label "#{ ... } - Set literals" :url (url "_sets")}

     {:label "'" :url (url "_quote")}
     {:label "\\ - Character literal prefix" :url (url "_character")}
     {:label "; - Line comment" :url (url "_comment")}
     {:label "@ - Dereference" :url (url "_deref")}
     {:label "^ - Metadata" :url (url "_metadata")}

     {:label ":foo - Keywords" :url (url "_literals")}
     {:label ":foo/bar - Qualified keywords" :url (url "_literals")}
     {:label "::bar - Reader qualified keywords" :url (url "_literals")}

     {:label "` - Syntax quote" :url syntax-url}
     {:label "~ - Unqote" :url syntax-url}
     {:label "~@ - Unquote splicing" :url syntax-url}

     {:label "# - Macro dispatch" :url dispatch-url}
     {:label "#\"...\" - Regex literals" :url dispatch-url}
     {:label "#'conj - Var literals" :url dispatch-url}
     {:label "#( % %1 %2 %3 ) - Fn reader literals" :url dispatch-url}

     {:label "#?(:clj ... :cljs ...) - Reader conditional" :url reader-cond-url}
     {:label "#?@(:clj ... :cljs ...) - Splicing reader conditional" :url reader-cond-url}
     ]))

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
                 (let [^String name  (t/thing->name t)
                       ^String pname (t/thing->name (t/thing->parent t))
                       ^String fname (str pname "/" name)]
                   (if (and name
                            (or (.contains name qstr)
                                (.contains fname qstr)))
                     {:label fname
                      :url   (web/make-html-url *cfg* t)
                      :fname fname}))))

         ;; Sort by textual distance
         (sort-by #(distance qstr (:fname %)))

         ;; Sort (stabily) by popularity
         (sort-by #(or (get (:fname %) defs)
                       (get (str "clj::" (:fname %)) defs)
                       (get (str "cljc::" (:fname %)) defs)
                       0))

         (take autocomplete-limit))))

(defn complete [qstr]
  (info (str "Completing " qstr))
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
