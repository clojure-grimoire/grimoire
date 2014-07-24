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
;; /resources/<clojure-version>/<namespace>/
;; /resources/<clojure-version>/<namespace>/<symbol>/source.clj
;; /resources/<clojure-version>/<namespace>/<symbol>/docs
;; /resources/<clojure-version>/<namespace>/<symbol>/examples.md
;; /resources/<clojure-version>/<namespace>/<symbol>/index.md

(defn file->ns [fpath]
  (-> fpath
      (replace #".clj$" "")
      (replace #"_" "-")
      (replace #"/" ".")))

(defn write-docs
  [root {:keys [symbol arglists doc src examples related]}]
  (let [sym-dir (io/file root symbol)]
    (.mkdir sym-dir)

    ;; write arities file
    (let [arities-file (io/file sym-dir "arities.txt")]
      (when-not (.exists arities-file)
        (doseq [l arglists]
          (spit arities-file (str l "\n") :append true))))

    ;; write docstring file
    (let [doc-file (io/file sym-dir "docstring.txt")]
      (when-not (.exists doc-file)
        (spit doc-file doc)))

    (let [extended-doc-file (io/file sym-dir "extended-docstring.txt")]
      (when-not (.exists extended-doc-file)
        (spit extended-doc-file "")))

    (when src
      ;; write source file
      (let [src-file (io/file sym-dir "source.clj")]
        (when-not (.exists src-file)
          (spit src-file src))))

    (let [ex-dir (io/file sym-dir "examples")]
      (when-not (.exists ex-dir)
        (.mkdir ex-dir)

        (when examples
          (doseq [{:keys [body] :as e} @examples]
            (let [fname (str (Math/abs (hash body)) ".clj")
                  f     (io/file ex-dir fname)]
              (spit f (-> body (replace #"</?pre>" ""))))))))

    (let [related-file (io/file sym-dir "related.txt")]
      (if related
        (doseq [{:keys [file name] :as el} @related]
          (let [file (or file "clojure/core.clj")]
            (spit related-file
                  (str (file->ns file) "/" name)
                  :append true)))
        (spit related-file "")))))

;; FIXME
;;   This should be a configuration value not hard coded.
(def var-blacklist
  #{#'clojure.data/Diff})

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
        :symbol      s
        :doc         doc
        :arglists    arglists
        :src         (#'clojure.repl/source-fn (symbol namespace raw-symbol))
        :examples    (when (= *version-str* "1.4.0")
                       (delay (-> (cd/examples-core namespace raw-symbol) :examples)))
        :related     (when (= *version-str* "1.4.0")
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
             :symbol      (my-munge (name sym))
             :arglists    (:forms fake-meta)
             :src         ";; Special forms have no source\n;; Implemented in the compiler."
             :examples    (when (= *version-str* "1.4.0")
                            (delay (-> (cd/examples-core "clojure.core" (name sym)) :examples)))
             :related     (when (= *version-str* "1.4.0")
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

        (let [namespaces (line-seq (io/reader input-file))]
          (doseq [n namespaces]
            (let [n (symbol n)]
              (require n)
              (write-docs-for-ns root n))))))))
