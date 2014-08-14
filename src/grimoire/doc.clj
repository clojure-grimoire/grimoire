(ns grimoire.doc
  (:refer-clojure :exclude [replace])
  (:require [cd-client.core :as cd]
            [clojure.java.io :as io]
            [clojure.java.io :refer :all]
            [clojure.string :refer [lower-case upper-case replace]]
            [grimoire.util :refer :all])

  ;; var resolution hack
  (:require [clojure.repl]
            [clojure.data]))

(def ^:dynamic *version-str* nil)

;; Intended file structure output
;;--------------------------------------------------------------------
;; /$VERSION/release-notes.md
;; /$VERSION/$NAMESPACE/ns-notes.md
;; /$VERSION/$NAMESPACE/$SYMBOL/name.txt
;; /$VERSION/$NAMESPACE/$SYMBOL/type.txt
;; /$VERSION/$NAMESPACE/$SYMBOL/arities.txt
;; /$VERSION/$NAMESPACE/$SYMBOL/docstring.md
;; /$VERSION/$NAMESPACE/$SYMBOL/extended-docstring.md
;; /$VERSION/$NAMESPACE/$SYMBOL/source.clj
;; /$VERSION/$NAMESPACE/$SYMBOL/related.txt
;; /$VERSION/$NAMESPACE/$SYMBOL/examples/$EXAMPLE_ID.clj

(defn file->ns [fpath]
  (-> fpath
      (replace #".clj$" "")
      (replace #"_" "-")
      (replace #"/" ".")))

(defn write-docs
  [root {:keys [raw-symbol symbol type arglists doc
                src examples related]}]
  (let [sym-dir (io/file root symbol)]
    (.mkdir sym-dir)

    ;; write the name file
    (let [name-file (io/file sym-dir "name.txt")]
      (when-not (.exists name-file)
        (spit name-file raw-symbol)))

    ;; write type file
    (let [type-file (io/file sym-dir "type.txt")]
      (when-not (.exists type-file)
        (spit type-file type)))

    ;; write arities file
    (let [arities-file (io/file sym-dir "arities.txt")]
      (when-not (.exists arities-file)
        (doseq [l arglists]
          (spit arities-file
                (str l "\n")
                :append true))))

    ;; write docstring file
    (let [doc-file (io/file sym-dir "docstring.md")]
      (when-not (.exists doc-file)
        (spit doc-file doc)))

    ;; touch extended docstring
    ;; (let [extended-doc-file (io/file sym-dir "extended-docstring.md")]
    ;;   (when-not (.exists extended-doc-file)
    ;;     (spit extended-doc-file "")))

    ;; write source file
    (when src
      ;; write source file
      (let [src-file (io/file sym-dir "source.clj")]
        (when-not (.exists src-file)
          (spit src-file src))))

    ;; write related file
    (let [related-file (io/file sym-dir "related.txt")]
      (when-not (.exists related-file)
        (when related
          (doseq [{:keys [file name] :as el} @related]
            (let [file (or file "clojure/core.clj")]
              (spit related-file
                    (str (file->ns file) "/" name "\n")
                    :append true)))
          (spit related-file ""))))

    ;; write examples from clojuredocs
    (let [ex-dir (io/file sym-dir "examples")]
      (when-not (.exists ex-dir)
        (.mkdir ex-dir)

        (when examples
          (doseq [{:keys [body] :as e} @examples]
            (let [fname (str (Math/abs (hash body)) ".clj")
                  f     (io/file ex-dir fname)]
              (spit f (-> body (replace #"</?pre>" ""))))))))))

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
  [input-file]
  (cd/set-web-mode!)

  (let [{:keys [major minor incremental]} *clojure-version*]
    (binding [*version-str* (format "%s.%s.%s" major minor incremental)]
      (let [resources (io/file "resources")
            root      (io/file resources *version-str*)]
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
              (write-docs-for-ns root n))))))))
