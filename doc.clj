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

(def munge-map
  ;; close enough, this is the compiler's munge map just gonna use
  ;; char literals since munge-map is gonna be applied over chars not
  ;; over strings

  ;; please go ahead and figure out how you want to structure kicking out files
  {\- "_",
   \. "_DOT_",
   \: "_COLON_",
   \+ "_PLUS_",
   \> "_GT_",
   \< "_LT_",
   \= "_EQ_",
   \~ "_TILDE_",
   \! "_BANG_",
   \@ "_CIRCA_",
   \# "_SHARP_",
   \' "_SINGLEQUOTE_",
   \" "_DOUBLEQUOTE_",
   \% "_PERCENT_",
   \^ "_CARET_",
   \& "_AMPERSAND_",
   \* "_STAR_",
   \| "_BAR_",
   \{ "_LBRACE_",
   \} "_RBRACE_",
   \[ "_LBRACK_",
   \] "_RBRACK_",
   \/ "_SLASH_",
   \\ "_BSLASH_",
   \? "_QMARK_"})

(defn munge
  [string]
  (->> string
       (replace munge-map)
       (into "") ;; lol hope this works
       ))

(defn write-docs-for-var
  "dir-root is gonna be /<version>/"
  [dir-root var]
  ;; need to escape . and .. here
  (let [target (file dir-root "/" (-> v .sym str) ".html") ;; FIXME: do I need the .html? are we writing .yaml or something?
        {:keys [argslist doc added] :as meta} (meta var)]

    )
  ;; write docstring file
  ;; write arity file
  ;; write metadata file?
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
