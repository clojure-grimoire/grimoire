(ns doc
  (:require [clojure.java.io :refer :all]))

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
;; /
;; /<clojure-version>/
;; /<clojure-version>/<namespace>/
;; /<clojure-version>/<namespace>/<symbol>/source.md
;; /<clojure-version>/<namespace>/<symbol>/docs.md      ;; <- aritiy strings goe here?
;; /<clojure-version>/<namespace>/<symbol>/examples.md  ;; <- user maintained, we just need to touch it
;; /<clojure-version>/<namespace>/<symbol>/index.md     ;; <-

(defn var-name
  [v]
  (symbol
   (-> v .ns ns-name str)
   (-> v .sym str)))

(defn print-vars
  [vars]
  (doseq [var vars]
    (println "***" (str (var-name var)))
    (println "")
    (println "**** Arities")
    (println "")
    (doseq [arity (:arglists (meta var))]
      (println arity))
    (println "")

    (println "**** Docs")
    (println "")
    (println (:doc (meta var)))
    (println "")))

(defn macro? [var]
  (:macro (meta var)))

(defn write-docs-for-var
  "dir-root is gonna be /<version>/"
  [dir-root var]
  (let [namespace (-> var .ns ns-name str)
        symbol    (-> var .sym str munge)
        target (file dir-root "/" symbol ".html") ;; FIXME: do I need the .html? are we writing .yaml or something?
        {:keys [arglists doc] :as meta} (meta var)
        source ]

    )
  ;; write docstring file
  (with-open [doc-file (file dir-root "/" symbol "/docs.md")]
    (spit doc-file
          (format "# Arities\n%s\n# Documentation\n%s"
                  (->> arities (interpose \newline) str)
                  doc)))

  ;; write metadata file?
  (with-open [meta-file (file dir-root "/" symbol "/docs.md")]
    ;; le ghetto JSON emitter
    (->> meta
         (map (comp str                            ;; MAP ALL THE THINGS
                    (partial map pr-str)
                    (partial apply interpose \:)))
         (interpose \,)
         (reduce str)
         (format "{%s}")
         (spit meta-file)))

  ;; write source file


  )

(defn write-docs-for-ns
  [version-dir ns]
  (let [core    (map second (ns-publics ns))
        macros  (filter macro? core)
        fns     (filter (comp not macro?) core)
        ns-root (file version-dir "/" (name ns))]

    (.mkdir ns-root) ;;

    ;; write per symbol docs
    (doseq [sym core]
      (write-docs-for-var ns-root sym))

    ;; write namespace index

    (let [index-file (file ns-root "/index.html")]
      (spit index-file
            (format "<h1>%s</h1><h2>Functions</h2><" (name ns)))
      (doseq [var core]
        (spit index-file
              (format "<a href=\"%s\">%s</a>"))
        )
      )

    ))

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
  (let [{:keys [major minor incremental]} *clojure-version*]
    (with-open [version-dir (file (format "%s.%s.%s" major minor incremental))]
      (.mkdir version-dir) ;; gonna need that

      (doseq [ns namespaces]
        (try (require ns)
             (write-docs-for-ns version-dir ns)
             (catch Exception e
               nil))))))
         nil))))))
(ns doc
  (:require [clojure.java.io :refer :all])  )

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
;; /
;; /<clojure-version>/
;; /<clojure-version>/<namespace>/
;; /<clojure-version>/<namespace>/<symbol>/source.md
;; /<clojure-version>/<namespace>/<symbol>/docs.md      ;; <- aritiy strings goe here?
;; /<clojure-version>/<namespace>/<symbol>/examples.md  ;; <- user maintained, we just need to touch it
;; /<clojure-version>/<namespace>/<symbol>/index.md     ;; <-

(defn var-name
  [v]
  (symbol
   (-> v .ns ns-name str)
   (-> v .sym str)))

(defn print-vars
  [vars]
  (doseq [var vars]
    (println "***" (str (var-name var)))
    (println "")
    (println "**** Arities")
    (println "")
    (doseq [arity (:arglists (meta var))]
      (println arity))
    (println "")

    (println "**** Docs")
    (println "")
    (println (:doc (meta var)))
    (println "")))

(defn macro? [var]
  (:macro (meta var)))

;; lol clojure.core/munge

(defn source
  [sym]
  )

(defn write-docs-for-var
  "dir-root is gonna be /<version>/"
  [dir-root var]
  (let [namespace (-> var .ns ns-name str)
        symbol    (-> var .sym str munge)
        target (file dir-root "/" symbol ".html") ;; FIXME: do I need the .html? are we writing .yaml or something?
        {:keys [arglists doc] :as meta} (meta var)
        source ]

    )
  ;; write docstring file
  (with-open [doc-file (file dir-root "/" symbol "/docs.md")]
    (spit doc-file
          (format "# Arities\n%s\n# Documentation\n%s"
                  (->> arities (interpose \newline) str)
                  doc)))

  ;; write metadata file?
  (with-open [meta-file (file dir-root "/" symbol "/docs.md")]
    (format "{%s}"
            (->> meta
                 (map (partial apply interpose \:))
                 (map (comp str (partial map )))

  ;; write source file
  )

(defn write-docs-for-ns
  [version-dir ns]
  (let [core    (map second (ns-publics ns))
        macros  (filter macro? core)
        fns     (filter (comp not macro?) core)
        ns-root (file version-dir "/" (name ns))]

    (.mkdir ns-root) ;;

    ;; write per symbol docs
    (doseq [sym core]
      (write-docs-for-var ns-root sym))

    ;; write namespace index

    (let [index-file (file ns-root "/index.html")]
      (spit index-file
            (format "<h1>%s</h1><h2>Functions</h2><" (name ns)))
      (doseq [var core]
        (spit index-file
              (format "<a href=\"%s\">%s</a>"))
        )
      )

    ))

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
  (let [{:keys [major minor incremental]} *clojure-version*]
    (with-open [version-dir (file (format "%s.%s.%s" major minor incremental))]
      (.mkdir version-dir) ;; gonna need that

      (doseq [ns namespaces]
        (try (require ns)
             (write-docs-for-ns version-dir ns)
             (catch Exception e
               nil))))))
         nil))))))
(ns doc
  (:require [clojure.java.io :refer :all])  )

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
;; /
;; /<clojure-version>/
;; /<clojure-version>/<namespace>/
;; /<clojure-version>/<namespace>/<symbol>/source.md
;; /<clojure-version>/<namespace>/<symbol>/docs.md      ;; <- aritiy strings goe here?
;; /<clojure-version>/<namespace>/<symbol>/examples.md  ;; <- user maintained, we just need to touch it
;; /<clojure-version>/<namespace>/<symbol>/index.md     ;; <-

(defn var-name
  [v]
  (symbol
   (-> v .ns ns-name str)
   (-> v .sym str)))

(defn print-vars
  [vars]
  (doseq [var vars]
    (println "***" (str (var-name var)))
    (println "")
    (println "**** Arities")
    (println "")
    (doseq [arity (:arglists (meta var))]
      (println arity))
    (println "")

    (println "**** Docs")
    (println "")
    (println (:doc (meta var)))
    (println "")))

(defn macro? [var]
  (:macro (meta var)))

;; lol clojure.core/munge
(defn source-fn
  "Returns a string of the source code for the given symbol, if it can
  find it.  This requires that the symbol resolve to a Var defined in
  a namespace for which the .clj is in the classpath.  Returns nil if
  it can't find the source.  For most REPL usage, 'source' is more
  convenient.

  Example: (source-fn 'filter)"
  [x]
  (when-let [v (resolve x)]
    (when-let [filepath (:file (meta v))]
      (when-let [strm (.getResourceAsStream (clojure.lang.RT/baseLoader) filepath)]
        (with-open [rdr (LineNumberReader. (InputStreamReader. strm))]
          (dotimes [_ (dec (:line (meta v)))] (.readLine rdr))
          (let [text (StringBuilder.)
                pbr (proxy [PushbackReader] [rdr]
                      (read [] (let [i (proxy-super read)]
                                 (.append text (char i))
                                 i)))]
            (read (PushbackReader. pbr))
            (str text)))))))

(defn write-docs-for-var
  "dir-root is gonna be /<version>/"
  [dir-root var]
  (let [namespace (-> var .ns ns-name str)
        symbol    (-> var .sym str munge)
        target (file dir-root "/" symbol ".html") ;; FIXME: do I need the .html? are we writing .yaml or something?
        {:keys [arglists doc] :as meta} (meta var)
        source ]

    )
  ;; write docstring file
  (with-open [doc-file (file dir-root "/" symbol "/docs.md")]
    (spit doc-file
          (format "# Arities\n%s\n# Documentation\n%s"
                  (->> arities (interpose \newline) str)
                  doc)))

  ;; write metadata file?
  (with-open [meta-file (file dir-root "/" symbol "/docs.md")]
    (format "{%s}"
            (->> meta
                 (map (partial apply interpose \:))
                 (map (comp str (partial map )))

  ;; write source file
  )

(defn write-docs-for-ns
  [version-dir ns]
  (let [core    (map second (ns-publics ns))
        macros  (filter macro? core)
        fns     (filter (comp not macro?) core)
        ns-root (file version-dir "/" (name ns))]

    (.mkdir ns-root) ;;

    ;; write per symbol docs
    (doseq [sym core]
      (write-docs-for-var ns-root sym))

    ;; write namespace index

    (let [index-file (file ns-root "/index.html")]
      (spit index-file
            (format "<h1>%s</h1><h2>Functions</h2><" (name ns)))
      (doseq [var core]
        (spit index-file
              (format "<a href=\"%s\">%s</a>"))
        )
      )

    ))

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
  (let [{:keys [major minor incremental]} *clojure-version*]
    (with-open [version-dir (file (format "%s.%s.%s" major minor incremental))]
      (.mkdir version-dir) ;; gonna need that

      (doseq [ns namespaces]
        (try (require ns)
             (write-docs-for-ns version-dir ns)
             (catch Exception e
               nil))))))
