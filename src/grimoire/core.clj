(ns grimoire.core
  (:refer-clojure :exclude [replace])
  (:require [clojure.java.io :refer :all]
            [clojure.string :refer [lower-case upper-case replace-first replace]]
            [clojure.repl]
            [cd-client.core :as cd])
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
  {;; 1.3.0 -> nil
   "1.4.0" "1.3.0"
   "1.5.0" "1.4.0",
   "1.6.0" "1.5.0"})

(defn my-munge [s]
  (-> s
      (replace "*" "_STAR_")
      (replace "?" "_QMARK_")
      (replace "." "_DOT_")
      (replace "<" "_LT_")
      (replace ">" "_GT_")
      (replace "-" "_DASH_")
      (replace "/" "_SLASH_")
      (replace "!" "_BANG_")
      (replace "=" "_EQ_")
      (replace "+" "_PLUS_")
      (replace "'" "_SQUOTE_")
      (replace #"^_*" "")
      (replace #"_*$" "")))

(defn write-example
  [i {:keys [body] :as example}]
  (str "### Example " i "\n"
       "[permalink](#example-" i ")\n"
       "\n"
       "{% highlight clojure linenos %}\n"
       "{% raw %}\n"
       (replace body #"[\t ]+\n" "\n")
       "{% endraw %}\n"
       "{% endhighlight %}\n\n\n"))

(defn write-docs
  [[ns-dir inc-dir]
   {:keys [namespace symbol raw-symbol arglists doc src version-str examples]}]
  (let [md-symbol (replace raw-symbol "*" "\\*")]
    (let [sym-inc-dir (file inc-dir symbol)]
      (.mkdir sym-inc-dir)

      ;; write docstring file
      (let [inc-doc-file (file sym-inc-dir "docs.md")]
        (->> (format (str "## Arities\n"
                          "%s\n\n"
                          "## Documentation\n"
                          "{%%raw%%}\n"
                          "%s\n"
                          "{%%endraw%%}\n")
                     (->> arglists
                          (interpose \newline)
                          (reduce str))
                     doc)
             (spit inc-doc-file)))

      (when src
        ;; write source file
        (let [inc-src-file (file sym-inc-dir "src.md")]
          (when-not (.exists inc-src-file)
            (->> (format (str (lq "highlight" "clojure" "linenos")
                              "%s\n"
                              (lq "endhighlight"))
                         src)
                 (spit inc-src-file)))))

      (let [ex-file (file sym-inc-dir "examples.md")]
        ;; ensure the examples file
        (when-not (.exists ex-file)
          (->> (str (let [v (prior-clojure-version version-str)
                          i (str v "/" namespace "/" symbol "/examples.md")
                          f (str "./_includes/" i)]
                      (when (.exists (file f))
                        (str "{% include " i " %}\n")))

                    (when (= version-str "1.4.0")
                      (->> @examples
                           (map-indexed write-example)
                           (reduce str)))

                    (when (= version-str "1.6.0")
                      (str "\n"
                           "[Please add examples!](https://github.com/arrdem/grimoire/edit/master/"
                           ex-file ")\n")))
               (spit ex-file))))

      ;; write template files
      ;; /<clojure-version>/<namespace>/<symbol>.md
      (let [dst-dir (file (str ns-dir "/" symbol))
            dst-file (file dst-dir "index.md")]
        (when-not (.exists dst-dir)
          (.mkdir dst-dir))

        (when-not (.exists dst-file)
          (->> (str (render-yaml [["layout"    "fn"]
                                  ["namespace" namespace]
                                  ["symbol"    (pr-str md-symbol)]])
                    (format "\n# [Clojure %s](../../)/[%s](../)/%s\n\n"
                            version-str namespace   md-symbol)
                    (lq "include" (trim-dot (str ns-dir "/" symbol "/docs.md")))
                    "\n##Examples\n\n"
                    (lq "include" (trim-dot (str ns-dir "/" symbol "/examples.md")))
                    (if src
                      (str "## Source\n"
                           (lq "include" (trim-dot (str ns-dir "/" symbol "/src.md"))))
                      "")
                    "\n")
               (format)
               (spit dst-file)))))))

(defn write-docs-for-var
  [[ns-dir inc-dir :as files] var]
  {:pre [(var? var)]}
  (let [namespace                         (-> var .ns ns-name str)
        raw-symbol                        (-> var .sym str)
        s                                 (my-munge raw-symbol)
        {:keys [arglists doc] :as meta}   (meta var)
        {:keys [major minor incremental]} *clojure-version*
        version-str                       (format "%s.%s.%s" major minor incremental)]
    (write-docs
     files
     {:version-str version-str
      :namespace   namespace
      :symbol      s
      :raw-symbol  raw-symbol
      :doc         doc
      :arglists    arglists
      :src         (#'clojure.repl/source-fn (symbol namespace raw-symbol))
      :examples    (when (= version-str "1.4.0")
                     (delay (-> (cd/examples-core namespace raw-symbol) :examples)))})))

(defn var->link
  [v]
  {:pre  [(var? v)]
   :post [(string? %)]}
  (format "[%s](%s)\n"
          (replace (var->name v) "*" "\\*")
          (str "./" (-> v var->name my-munge) "/")))

(defn sym->link
  [s]
  {:pre  [(symbol? s)]
   :post [(string? %)]}
  (format "[%s](%s)\n"
          (replace (name s) "*" "\\*")
          (str "./" (-> s name my-munge) "/")))

(defn index-vars
  [var-seq]
  {:pre  [(every? var? var-seq)]
   :post [(string? %)]}
  (let [f      (comp first var->name)
        blocks (group-by f var-seq)
        blocks (sort-by first blocks)]
    (->> (for [[heading vars] blocks]
           (str (format "### %s\n" (-> heading upper-case str))
                (->> (for [var (sort-by var->name vars)]
                       (var->link var))
                     (reduce str))))
         (interpose "\n")
         (reduce str))))

(defn write-docs-for-specials
  [files]
  (let [{:keys [major minor incremental]} *clojure-version*
        version-str                       (format "%s.%s.%s" major minor incremental)]
    (doseq [[sym fake-meta] @#'clojure.repl/special-doc-map]
      (write-docs
       files
       (-> fake-meta
           (assoc
               :version-str version-str
               :namespace   'clojure.core
               :symbol      (my-munge (name sym))
               :raw-symbol  sym
               :arglists    (:forms fake-meta)
               :src         ";; Special forms have no source\n;; Implemented in the compiler."
               :examples    (delay (-> (cd/examples-core "clojure.core" (name sym)) :examples)))))
      (println "Documented special form" sym))))


(defn index-specials
  []
  (let [var-seq (keys @#'clojure.repl/special-doc-map)
        f       (comp first name)
        blocks  (group-by f var-seq)
        blocks  (sort-by first blocks)]
    (->> (for [[heading vars] blocks]
           (str (format "### %s\n" (-> heading upper-case str))
                (->> (for [var (sort-by name vars)]
                       (sym->link var))
                     (reduce str))))
         (interpose "\n")
         (reduce str))))

(defn write-docs-for-ns
  [dirs ns]
  (let [[version-dir include-dir]         dirs
        ns-vars                           (map second (ns-publics ns))
        macros                            (filter macro? ns-vars)
        fns                               (filter #(and (fn? @%1)
                                                        (not (macro? %1)))
                                                  ns-vars)
        vars                              (filter #(not (fn? @%1)) ns-vars)
        {:keys [major minor incremental]} *clojure-version*
        version-str                       (format "%s.%s.%s" major minor incremental)]
    (let [version-ns-dir (file version-dir (name ns))
          include-ns-dir (file include-dir (name ns))
          files          [version-ns-dir include-ns-dir]]
      (.mkdir version-ns-dir)
      (.mkdir include-ns-dir)

      ;; write per symbol docs
      (doseq [var ns-vars]
        (try
          (write-docs-for-var files var)
          (println "Documented" var)
          (catch java.lang.AssertionError e
            (println "Warning: Failed to write docs for" var))))

      (when (= ns 'clojure.core)
        (write-docs-for-specials files))

      ;; write namespace index
      (let [index-file (file version-ns-dir "index.md")
            index-inc-file (file include-ns-dir "index.md")]

        (when-not (.exists index-inc-file)
          (->> (str "No namespace specific documentation!\n"
                    "\n"
                    "[Please add commentary!](https://github.com/arrdem/grimoire/edit/master/"
                    index-inc-file ")\n\n")
               (spit index-inc-file)))

        (let [f (file index-file)]
          (when-not false (.exists f)
            (->> (str (render-yaml [["layout" "ns"]])
                      (format "# [Clojure %s](../)/%s\n\n" version-str (str ns))

                      (str "{% markdown " version-ns-dir  "/index.md %}\n\n")

                      (when (= ns 'clojure.core)
                        (str "## Special Forms <a id=\"sff\">+</a>\n\n"
                             "<div id=\"sforms\" markdown=\"1\">\n\n"
                             (index-specials)
                             "\n</div>\n"))

                      "\n\n"

                      (when macros
                        (str "## Macros <a id=\"mf\">+</a>\n\n"
                             "<div id=\"macros\" markdown=\"1\">\n\n"
                             (index-vars macros)
                             "\n</div>\n"))

                      "\n\n"

                      (when vars
                        (str "## Vars <a id=\"vf\">+</a>\n\n"
                             "<div id=\"vars\" markdown=\"1\">\n\n"
                             (index-vars vars)
                             "\n</div>\n"))

                      "\n\n"

                      (when fns
                        (str "## Functions <a id=\"ff\">+</a>\n\n"
                             "<div id=\"fns\" markdown=\"1\">\n\n"
                             (index-vars fns)
                             "\n</div>\n")))
                 (spit f)))))))

  (println "Finished" ns)
  nil)

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
  (cd/set-web-mode!)

  (let [{:keys [major minor incremental]} *clojure-version*
        version-str                       (format "%s.%s.%s" major minor incremental)]
    (println version-str)
    (let [version-dir (file version-str)
          include-dir (file (str "_includes/" version-str))]
      (when-not (.exists version-dir)
        (.mkdir version-dir))

      (when-not (.exists include-dir)
        (.mkdir include-dir))

      (println "Made root folders...")

      (doseq [n namespaces]
        (when-not (and (= n 'clojure.edn)
                       (= version-str "1.4.0"))
          (require n)
          (write-docs-for-ns [version-dir include-dir] n)))

      (let [version-inc-file (file include-dir "index.md")]
        (when-not (.exists version-inc-file)
          (->> (str "No release specific documentation!\n"
                    "\n"
                    "[Please add changelog!](https://github.com/arrdem/grimoire/edit/master/"
                    version-inc-file ")\n\n")
               (spit version-inc-file))))

      (let [version-file (file version-dir "index.md")]
        (when-not (.exists version-file)
          (->> (str (render-yaml [["layout"  "release"]
                                  ["version" version-str]])
                    "\n"
                    "## Release information\n"
                    "\n"
                    (str "{% markdown " version-file " %}\n")
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
               (spit version-file)))))))
