(ns doc
  (:require [clojure.java.io :refer :all]
            [clojure.string :refer [lower-case upper-case replace-first replace]])
  (:import [java.io LineNumberReader InputStreamReader PushbackReader]
           [clojure.lang RT]))

;; Intended file structure output
;;--------------------------------------------------------------------
;;
;; Do we want to kick out raw HTML, or do we want to kick out markdown
;; that we can run through jekyll or something else to use pygments?
;; I'm personally a fan of how pygments treats the Clojure code I
;; throw at it for my blog.
;;
;; It may be worthwhile to provide a litte Javascript snippet that can
;; munge a given fully qualified name if we have a search feature at
;; all.
;;
;; Everything under /_include/ is where the data goes
;;
;; /<clojure-version>/ is to be YAML templates built from include
;; data. There may even be a way to get jekyll to build all that.
;;
;; /
;; /_include/<clojure-version>/<namespace>/
;; /_include/<clojure-version>/<namespace>/<symbol>/source.md
;; /_include/<clojure-version>/<namespace>/<symbol>/docs.md
;; /_include/<clojure-version>/<namespace>/<symbol>/examples.md
;; /_include/<clojure-version>/<namespace>/<symbol>/index.md
;; /<clojure-version>/
;; /<clojure-version>/<namespace>/
;; /<clojure-version>/<namespace>/<symbol>/

(defn var->name [v]
  {:pre [(var? v)]}
  (-> v .sym str))

(defn var->ns [v]
  {:pre [(var? v)]}
  (-> v .ns ns-name str))

(defn macro? [v]
  {:pre [(var? v)]}
  (:macro (meta v)))

(defn debug-dir [dir]
  )

;; clojure.repl/source-fn
(defn source-fn
  "Returns a string of the source code for the given symbol, if it can
  find it.  This requires that the symbol resolve to a Var defined in
  a namespace for which the .clj is in the classpath.  Returns nil if
  it can't find the source.  For most REPL usage, 'source' is more
  convenient.

  Example: (source-fn #'clojure.core/filter)"
  [v]
  {:pre [(var? v)]
   :post [(string? %)]}
  (when-let [filepath (:file (meta v))]
    (when-let [strm (.getResourceAsStream (RT/baseLoader) filepath)]
      (with-open [rdr (LineNumberReader. (InputStreamReader. strm))]
        (dotimes [_ (dec (:line (meta v)))] (.readLine rdr))
        (let [text (StringBuilder.)
              pbr (proxy [PushbackReader] [rdr]
                    (read [] (let [i (proxy-super read)]
                               (.append text (char i))
                               i)))]
          (read (PushbackReader. pbr))
          (str text))))))

(defn lq [& more]
  (str "{%% " (reduce str (interpose " " more)) " %%}\n"))

(defn render-yaml
  [mapping]
  (str "---\n"
       (->> mapping
            (map (fn [[k v]] (str k ": " v "\n")))
            (reduce str))
       "---\n"))

(defn trim-dot
  [s]
  (replace-first s "./" ""))

(def prior-clojure-version
  {"1.6.0" "1.5.1"
   "1.5.1" "1.5.0"
   "1.5.0" "1.4.0"})

(defn my-munge [s]
  (-> s
      munge
      (replace "*" "\\*")
      (replace "." "_DOT_")
      (replace #"^_*" "")
      (replace #"_*$" "")
      lower-case))

(defn write-docs-for-var
  [[ns-dir inc-dir] var]
  {:pre [(var? var)]}
  (let [namespace                         (-> var .ns ns-name str)
        raw-symbol                        (-> var .sym str)
        symbol                            (my-munge raw-symbol)
        md-symbol                         (replace raw-symbol "*" "\\*")
        {:keys [arglists doc] :as meta}   (meta var)
        {:keys [major minor incremental]} *clojure-version*
        version-str                       (format "%s.%s.%s" major minor incremental)]
    (let [sym-inc-dir (file inc-dir symbol)]
      (.mkdir sym-inc-dir)

      (debug-dir sym-inc-dir)

      ;; write docstring file
      (let [inc-doc-file (file sym-inc-dir "docs.md")]
        (->> (format (str "## Arities\n"
                          "%s\n\n"
                          "## Documentation\n"
                          "%s\n")
                     (->> arglists
                          (interpose \newline)
                          (reduce str))
                     doc)
             (spit inc-doc-file)))

      (when (fn? @var)
        ;; write source file
        (let [inc-src-file (file sym-inc-dir "src.md")]
          (->> (format (str (lq "highlight" "clojure" "linenos")
                            "%s\n"
                            (lq "endhighlight"))
                       (source-fn var))
               (spit inc-src-file))))

      (let [ex-file (file sym-inc-dir "examples.md")]
        ;; ensure the examples file
        (when-not (.exists ex-file)
          (->> (str (let [v (prior-clojure-version version-str)
                          i (str v "/" namespace "/" symbol "/examples.md")
                          f (str "./_includes/" i)]
                      (if (.exists (file f))
                        (str "{% include " i " %}\n")
                        (str "No examples for version " version-str " or newer\n"
                             "\n"
                             "[Please add examples!](https://github.com/arrdem/grimoire/edit/master/_includes/"
                                                     version-str "/" namespace "/" symbol "/examples.md)\n"))))
               (spit ex-file)))))

    ;; write template files
    ;; /<clojure-version>/<namespace>/<symbol>.md
    (let [dst-dir (file (str ns-dir "/" symbol))
          dst-file (file dst-dir "index.md")]
      (.mkdir dst-dir)

      (->> (str (render-yaml [["layout"    "fn"]
                              ["namespace" namespace]
                              ["symbol"    (pr-str md-symbol)]])
                (format "\n# [%s](../)/%s\n\n"
                        namespace
                        md-symbol)
                (lq "include" (trim-dot (str ns-dir "/" symbol "/docs.md")))
                "\n##Examples\n\n"
                (lq "include" (trim-dot (str ns-dir "/" symbol "/examples.md")))
                (if (fn? @var)
                  (str "## Source\n"
                       (lq "include" (trim-dot (str ns-dir "/" symbol "/src.md"))))
                  "")
                "\n")
           (format)
           (spit dst-file)))))

(defn var->link
  [v]
  {:pre  [(var? v)]
   :post [(string? %)]}
  (format "[%s](%s)\n"
          (replace (var->name v) "*" "\\*")
          (str "./" (-> v var->name my-munge) "/")))

(defn index-vars
  [var-seq]
  {:pre  [(every? var? var-seq)]
   :post [(string? %)]}
  (let [f      (comp first var->name)
        blocks (group-by f var-seq)]
    (->> (for [[heading vars] blocks]
           (str (format "### %s\n\n" (-> heading upper-case str))
                (->> (for [var (sort-by var->name vars)]
                       (var->link var))
                     (reduce str))))
         (interpose "\n\n")
         (reduce str))))

(defn write-docs-for-ns
  [dirs ns]
  (let [[version-dir include-dir] dirs
        ns-vars                   (map second (ns-publics ns))
        macros                    (filter macro? ns-vars)
        fns                       (filter #(and (fn? @%1)
                                                (not (macro? %1)))
                                          ns-vars)
        vars                      (filter #(not (fn? @%1)) ns-vars)]
    (let [version-ns-dir  (file version-dir (name ns))
          include-ns-dir  (file include-dir (name ns))]
      (.mkdir version-ns-dir)
      (debug-dir version-ns-dir)

      (.mkdir include-ns-dir)
      (debug-dir include-ns-dir)

      ;; write per symbol docs
      (doseq [var ns-vars]
        (try
          (write-docs-for-var [version-ns-dir include-ns-dir] var)
          (catch java.lang.AssertionError e
            (println "Warning: Failed to write docs for" var))))

      ;; write namespace index
      (let [index-file (file version-ns-dir (str "index.md"))]
        (let [f (file index-file)]
          (->> (str (render-yaml [["layout" "ns"]
                                  ["title"  (name ns)]])
                    "## Macros\n\n"
                    (index-vars macros)

                    "\n\n## Vars\n\n"
                    (index-vars vars)

                    "\n\n## Functions\n\n"
                    (index-vars fns))
               (spit f)))))))

(def namespaces
  [;; Clojure "core"
   'clojure.core
   'clojure.data
   'clojure.edn
   'clojure.instant
   'clojure.pprint
   'clojure.reflect
   'clojure.repl
   'clojure.set
   'clojure.stacktrace
   'clojure.string
   'clojure.template
   'clojure.test
   'clojure.uuid
   'clojure.walk
   'clojure.xml
   'clojure.zip

   ;; Clojure JVM host interop
   'clojure.java.browse
   'clojure.java.io
   'clojure.java.javadoc
   'clojure.java.shell

   ;; Clojure test
   'clojure.test.junit
   'clojure.test.tap
   ])

(defn -main
  []
  (let [{:keys [major minor incremental]} *clojure-version*
        version-str                       (format "%s.%s.%s" major minor incremental)]
    (println version-str)
    (let [version-dir (file (str "./" version-str))
          include-dir (file (str "./_includes/" version-str))]
      (.mkdir version-dir)
      (debug-dir version-dir)
      (.mkdir include-dir)
      (debug-dir include-dir)

      (println "Made root folders...")

      (doseq [n namespaces]
        (when-not (and (= n 'clojure.edn)
                       (= version-str "1.4.0"))
          (require n)
          (write-docs-for-ns [version-dir include-dir] n)))

      (let [version-file (file version-dir "index.md")]
        (->> (str (render-yaml [["layout"  "release"]
                                ["version" version-str]])
                  "\n"
                  "## Release information\n"
                  "\n"
                  "## Namespaces\n"
                  "\n"
                  (->> namespaces
                       (filter (fn [n]
                                 (not (and (= n 'clojure.edn)
                                           (= version-str "1.4.0")))))
                       (map name)
                       (map #(format "- [%s](./%s/)\n" %1 %1))
                       (reduce str)))
             (spit version-file))))))

(-main)
