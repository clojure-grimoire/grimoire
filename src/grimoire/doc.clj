(ns grimoire.doc
  (:refer-clojure :exclude [replace])
  (:require [cd-client.core :as cd]
            [clojure.java.io :as io]
            [clojure.string :refer [lower-case upper-case replace]]
            [grimoire.util :refer :all]
            [grimoire.datastore :refer [write-docs]])

  ;; var resolution hack
  (:require [clojure.repl]
            [clojure.data]))

(def ^:dynamic *version-str* nil)

;; FIXME
;;   This should be a configuration value not hard coded.
(def var-blacklist
  #{#'clojure.data/Diff})

(defn var-type
  [v]
  {:pre [(var? v)]}
  (let [m (meta v)]
    (cond (:macro m)   "macro"
          (:dynamic m) "var"
          (fn? @v)     "fn"
          :else        "var")))

(defn write-docs-for-var
  [root var]
  {:pre [(var? var)]}
  (let [namespace                         (-> var .ns ns-name str)
        raw-symbol                        (-> var .sym str)
        s                                 (my-munge raw-symbol)
        {:keys [arglists doc] :as meta}   (meta var)]
    (when-not (var-blacklist var)
      (write-docs
       root
       {:*version-str* *version-str*
        :namespace     namespace
        :raw-symbol    raw-symbol
        :symbol        s
        :type          (var-type var)
        :doc           doc
        :arglists      arglists
        :src           (#'clojure.repl/source-fn (symbol namespace raw-symbol))
        :examples      (when (= *version-str* "1.4.0")
                         (delay (-> (cd/examples-core namespace raw-symbol) :examples)))
        :related       (when (= *version-str* "1.4.0")
                         (delay (cd/see-also-core namespace raw-symbol)))})
      (println "Documented var" raw-symbol))))

(defn write-docs-for-specials
  [root]
  (doseq [[sym fake-meta] @#'clojure.repl/special-doc-map]
    (write-docs
     root
     (-> fake-meta
         (assoc
             :*version-str* *version-str*
             :namespace     "clojure.core"
             :raw-symbol    (name sym)
             :symbol        (my-munge (name sym))
             :type          "special"
             :arglists      (:forms fake-meta)
             :src           ";; Special forms have no source\n;; Implemented in the compiler."
             :examples      (when (= *version-str* "1.4.0")
                              (delay (-> (cd/examples-core "clojure.core" (name sym)) :examples)))
             :related       (when (= *version-str* "1.4.0")
                              (delay (cd/see-also-core "clojure.core" (name sym)))))))
    (println "Documented special form" sym)))

(defn write-docs-for-ns
  [root ns]
  (let [ns-vars (->> (ns-publics ns) vals (remove var-blacklist))
        macros  (filter macro? ns-vars)
        fns     (filter #(and (fn? @%1)
                              (not (macro? %1)))
                        ns-vars)
        vars    (filter #(not (fn? @%1)) ns-vars)
        ns-dir  (io/file root (name ns))]
    (.mkdir ns-dir)

    (let [ns-notes (io/file ns-dir "ns-notes.md")]
      (when-not (.exists ns-notes)
        (spit ns-notes
              (format "Please add namespace commentary!\n"
                      (name ns)))))

    ;; write per symbol docs
    (doseq [var ns-vars]
      (write-docs-for-var ns-dir var))

    (when (= ns 'clojure.core)
      (write-docs-for-specials ns-dir)))

  (println "Finished" ns)
  nil)

(defn -main
  [groupid artifact version input-file]
  (cd/set-web-mode!)
  (binding [*version-str* version]
    (let [resources (io/file "resources")
          root      (io/file (str "resources/datastore/" groupid "/" artifact "/" *version-str*))]
      (when-not (.exists resources)
        (.mkdir resources))
      
      (when-not (.exists root)
        (.mkdir root))
      
      (let [release-notes (io/file root "release-notes.md")]
        (when-not (.exists release-notes)
          (spit release-notes 
                (str 
                 (format "[Official release notes](https://github.com/clojure/clojure/blob/clojure-%s/changes.md)\n"
                         *version-str*)
                 "\n"
                 "Please add release notes commentary!\n"))))
      
      (let [namespaces (line-seq (io/reader input-file))]
        (doseq [n namespaces]
          (let [n (symbol n)]
            (require n)
            (write-docs-for-ns root n)))))))
