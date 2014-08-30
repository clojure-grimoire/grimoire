(ns grimoire.doc
  (:refer-clojure :exclude [replace])
  (:require [clojure.java.io :as io]
            [clojure.string :refer [lower-case upper-case replace]]
            [cd-client.core :as cd]
            [me.raynes.fs :as fs]
            [grimoire.util :refer :all]
            [grimoire.datastore :refer [write-docs]])

  ;; var resolution hack
  (:require [clojure.repl]
            [clojure.data]))


;; FIXME
;;   This should also not be hard coded
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
  [[groupid artifact version] var]
  {:pre [(var? var)]}
  (let [namespace                         (-> var .ns ns-name str)
        raw-symbol                        (-> var .sym str)
        s                                 (my-munge raw-symbol)
        {:keys [arglists doc] :as meta}   (meta var)]
    (when-not (var-blacklist var)
      (write-docs groupid artifact version
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
  [[groupid artifact version] ]
  (doseq [[sym fake-meta] @#'clojure.repl/special-doc-map]
    (write-docs groupid artifact version
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
  [[groupid artifact version] ns]
  (let [ns-vars (->> (ns-publics ns) vals (remove var-blacklist))
        macros  (filter macro? ns-vars)
        fns     (filter #(and (fn? @%1)
                              (not (macro? %1)))
                        ns-vars)
        vars    (filter #(not (fn? @%1)) ns-vars)]

    ;; FIXME
    ;;   I feel like this needs to be lifted out somehow
    (let [path     (->> ["resources" "datastore" groupid artifact version "ns" (name ns)]
                      (interpose \/)
                      (apply str))
          ns-dir   (io/file path)
          ns-notes (io/file ns-dir "ns-notes.md")]
      (fs/mkdirs ns-dir)

      (when-not (.exists ns-notes)
        (spit ns-notes
              (format "Please add namespace commentary!\n"
                      (name ns)))))

    ;; write per symbol docs
    (doseq [var ns-vars]
      (write-docs-for-var [groupid artifact version] var))

    (when (and (= ns 'clojure.core)
               (= "org.clojure" groupid)
               (= "clojure" artifact)
      (write-docs-for-specials [groupid artifact version]))))

  (println "Finished" ns)
  nil)

(defn -main
  [groupid artifact version input-file]
  (cd/set-web-mode!)
  (let [namespaces (line-seq (io/reader input-file))]
    (doseq [n namespaces]
      (let [n (symbol n)]
        (require n)
        (write-docs-for-ns [groupid artifact version] n)))))
