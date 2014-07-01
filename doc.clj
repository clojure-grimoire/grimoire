(ns doc
  (:require [clojure.java.io :refer :all])
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

(defn var->name [v] (-> v .sym str))
(defn var->ns   [v] (-> v .ns ns-name str))
(defn macro?    [v] (:macro (meta v)))

;; clojure.repl/source-fn
(defn source-fn
  "Returns a string of the source code for the given symbol, if it can
  find it.  This requires that the symbol resolve to a Var defined in
  a namespace for which the .clj is in the classpath.  Returns nil if
  it can't find the source.  For most REPL usage, 'source' is more
  convenient.

  Example: (source-fn #'clojure.core/filter)"
  [v]
  {:pre  [(var? v)]
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
  (str "{% " (reduce str (interpose " " more)) " %}"))

(defn render-yaml
  [mapping]
  (str "---\n"
       (->> mapping
            (map (fn [k v] (str k ": " v)))
            (reduce str))
       "---\n"))

(defn write-docs-for-var
  [[ns-dir inc-dir] var]
  (let [namespace                       (-> var .ns ns-name str)
        symbol                          (-> var .sym str munge)
        {:keys [arglists doc] :as meta} (meta var)
        source                          (source-fn symbol)]

    ;; write docstring file
    (with-open [inc-doc-file (file inc-dir (str "/" symbol "/docs.md"))]
      (->> (format (str "## Arities\n"
                        "%s\n"
                        "## Documentation\n"
                        "%s")
                   (->> arglists (interpose \newline) str)
                   doc)
           (spit inc-doc-file)))

    ;; write source file
    (with-open [inc-src-file (file inc-dir (str "/" symbol "/src.md"))]
      (->> (format (str "## source\n"
                        (lq "highlight" "clojure" "linenos")
                        "%s\n"
                        (lq "endhighlight"))
                   (source-fn var)
           (spit inc-src-file))))

    ;; write template files
    ;; /<clojure-version>/<namespace>/<symbol>.md
    ))

(defn write-docs-for-ns
  [dirs ns]
  (let [[version-dir include-dir] dirs
        ns-vars                   (map second (ns-publics ns))
        macros                    (filter macro? ns-vars)
        fns                       (filter (comp not macro?) ns-vars)]
    (with-open [version-ns-dir  (file version-dir "/" (name ns))
                include-ns-dir  (file include-dir "/" (name ns))]
      (.mkdir version-ns-dir)
      (.mkdir include-ns-dir)

      ;; write per symbol docs
      (doseq [var ns-vars]
        (write-docs-for-var [version-ns-dir include-ns-dir] var))

      ;; write namespace index
      (let [index-file (file version-ns-dir ".md")]
        )

      )))

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
    (with-open [version-dir (file (str "./" version-str))
                include-dir (file (str "./_include/" version-str))]
      (.mkdir version-dir)
      (.mkdir include-dir)

      (doseq [ns namespaces]
        (try (require ns)
             (write-docs-for-ns [version-dir include-dir] ns)
             (catch Exception e
               (println e)
               nil))))))
