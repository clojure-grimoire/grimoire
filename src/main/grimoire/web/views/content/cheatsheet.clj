(ns grimoire.web.views.content.cheatsheet
  (:refer-clojure :exclude [munge])
  (:require [clojure.core.match :refer [match]]
            [grimoire.web.views :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.api.web :as web]
            [grimoire.either :refer [succeed? result]]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as u]
            [grimoire.util :refer [munge]]
            [grimoire.web.config :as cfg]
            [grimoire.github :as gh]
            [simpledb.core :as sdb]))

;; Helpers
;;------------------------------------------------------------------------------

(defn →thing [s]
  {:pre [(string? s)]}
  (let [t (t/url-path->thing s)]
    (web/make-html-url (cfg/web-config) t)))

(defn →
  ([s]
   [:a {:href s} s])

  ([a t]
   [:a {:href a} t]))

(def ^:dynamic *prefix*
  nil)

(defn →clj
  ([s]
   {:pre [(string? s)]}
   (if (.startsWith s "clojure.")
     (→ (→thing (str "org.clojure/clojure/latest/clj/" s)) s)
     (let [s (str *prefix* s)]
       (→ (→thing (str "org.clojure/clojure/latest/clj/clojure.core/" (munge s))) s))))

  ([l s]
   (→ (→thing (str "org.clojure/clojure/latest/clj/" (munge l))) s)))

(defn code [& forms]
  `[:code {} ~@forms])

(defn table [& forms]
  `[:table {} ~@forms])

(defmacro with-common-prefix [p & forms]
  `(with-bindings [*prefix* ~p]
     []))

(defn render-fragment [[kwd opts & body]]
  {:pre [(keyword? kwd)
         (map? opts)]}
  (match [kwd]
    [:box]
    ,,`[:div ~(-> opts
                  (update :class #(str % " box"))
                  )
        ~@(when-let [title (:title opts)]
            [[:h2 title]])
        ~@(map render-fragment body)]

    [(:or :section :subsection)]
    ,,`[:div ~(update opts :class #(str % " " (name kwd)))
        ~@(when-let [title (:title opts)]
            [[:h3 title]])
        ~@(map render-fragment body)]

    [:table]
    ,,`[:table ~opts
        ~@(when-let [title (:title opts)]
            [[:h4 title]])
        [:tbody ~opts
         ~@(map render-fragment body)]]

    [:row]
    ,,`[:tr ~opts
        ~@(when-let [title (:title opts)]
            [[:td title]])
        [:td ~@(interpose " " body)]]))

;; Fragments
;;------------------------------------------------------------------------------

(def repl
  (u/softref-cached
   [:box {:class "grid-item"}
    [:section {}
     [:subsection {:title "REPL Tools"}
      [:table {}
       [:row {:title "clojure.repl/"}
        (→clj "clojure.repl/doc" "doc")
        (→clj "clojure.repl/find-doc" "find-doc")
        (→clj "clojure.repl/apropos" "apropos")
        (→clj "clojure.repl/source" "source")
        (→clj "clojure.repl/pst" "pst")
        (→clj "clojure.java.javadoc/javadoc")]

       `[:row {:title "REPL Vars"}
         ~@(map →clj ["*1" "*2" "*3" "*e"])]

       `[:row {:title "Option Vars"}
         ~@(map →clj ["*print-dup*" "*print-length*" "*print-level*"
                      "*print-meta*" "*print-readably*"
                      "*compile-files*" "*compile-path*" "*file*"
                      "*warn-on-reflection*"])]

       `[:row {:title "Misc"}
         ~@(map →clj ["compile" "gen-class" "gen-interface"
                      "loaded-libs" "test" "eval" "force" "hash"
                      "name" "*clojure-version*" "clojure-version"
                      "*command-line-args*"])]

       `[:row {:title "Browser / Shell"}
         ~@(map →clj ["clojure.java.browse/browse-url"
                      "clojure.java.shell/sh"
                      "clojure.java.shell/with-sh-dir"
                      "clojure.java.shell/with-sh-env"])]]]]]))

(def numbers
  (u/softref-cached
   [:box {:class "grid-item"
          :title "Numbers"}
    [:section {}
     [:subsection {:title (→ "http://docs.oracle.com/javase/7/docs/api/java/lang/Long.html" "Long literals")}
      [:table {}
       [:row {:title "decimal"} (code "7")]
       [:row {:title "hexadecimal"} (code "0xff")]
       [:row {:title "octal"} (code "017")]
       [:row {:title "binary"} (code "2r1011")]
       [:row {:title "base 36"} (code "36rCRAZY")]]]

     [:subsection {:title "Other number literals"}
      [:table {:title ""}
       [:row {:title "BigInteger"} (code "7N")]
       [:row {:title "Ratio"} (code "-22/7")]
       [:row {:title (→ "http://docs.oracle.com/javase/7/docs/api/java/lang/Double.html" "Double")}
        (code "2.78") (code "-1.2e-5")]
       [:row {:title (→ "http://docs.oracle.com/javase/7/docs/api/java/math/BigDecimal.html" "BigDecimal:")}
        (code "4.2M") (code "5M")]]]

     [:subsection {:title "Operations"}
      [:table {}
       `[:row {:title "Arithmetic"}
         ~@(map →clj ["+" "-" "*" "/" "quot" "rem" "mod" "inc" "dec" "max" "min"
                      "+'" "-'" "*'" "inc'" "dec'"])]

       `[:row {:title "Compare"}
         ~@(map →clj ["==" "<" ">" "<=" ">=" "compare"])]

       `[:row {:title "Bitwise"}
         ~@(map →clj ["bit-and" "bit-or" "bit-xor" "bit-not" "bit-flip" "bit-set"
                      "bit-shift-right" "bit-shift-left" "bit-and-not" "bit-clear"
                      "bit-test"])

         [:br] "(1.6)" ~(→clj "bit-unsigned-bit-shift-right")
         "(see " ~(→ "http://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html" "BigInteger") " for integers larger than Long)"]

       `[:row {:title "Cast"}
         ~@(map →clj ["byte" "short" "int" "long" "float" "double" "bigdec" "bigint"
                      "num" "rationalize" "biginteger"])]

       `[:row {:title "Test"}
         ~@(map →clj ["zero?" "pos?" "neg?" "even?" "odd?" "number?" "rational?"
                      "integer?" "ratio?" "decimal?" "float?"])]

       `[:row {:title "Random"}
         ~@(map →clj ["rand" "rand-int"])]

       [:row {:title "BigDecimal"}
        (→clj "with-precision")]

       ;; TBD: Why do these not exist in Clojure?
       ;; There are -int versions, but not long
       ;; versions.  unchecked-divide
       ;; unchecked-remainder.  Filed ticket CLJ-1545
       ;; to add them.

       `[:row {:title "Unchecked"}
         ~(→clj "*unchecked-math*")
         ~@(map (comp →clj (partial str "unchecked-"))
                ["add" "dec" "inc" "multiply" "negate" "subtract"])]]]]]))

(def functions
  (u/softref-cached
   [:box {:class "grid-item"}
    [:section {:title "Functions"}
     [:table {}
      `[:row {:title "Create"}
        ~@(map →clj ["fn" "defn" "defn-" "definline" "identity" "constantly" "memfn" "comp" "complement"
                     "partial" "juxt" "memoize" "fnil" "every-pred" "some-fn"])]

      `[:row {:title "Call"}
        ~@(map →clj ["apply" "->" "->>" "trampoline" ])
        [:br] "(1.5)"
        ~@(map →clj ["as->" "cond->" "cond->>" "some->" "some->>"])]

      `[:row {:title "Test"}
        ~@(map →clj ["fn?" "ifn?"])]]]]))

(def abstractions
  (u/softref-cached
   [:box {:title "Abstractions (<a href=\"https://github.com/cemerick/clojure-type-selection-flowchart\">Clojure type selection flowchart</a>)"
          :class "grid-item"}
    [:section {:title "Protocols (<a href=\"http://clojure.org/protocols\">clojure.org/protocols</a>)"}
     [:table {}
      [:row {:title "Define"}
       "<code>(</code>"
       (→clj "defprotocol")
       "<code>Slicey (slice [at]))</code>"]

      [:row {:title "Extends"}
       "<code>(</code>"
       (→clj "extend-type")
       "<code>String Slicey (slice [at] ...))</code>"]

      [:row {:title "Extend null"}
       "<code>(</code>"
       (→clj "extend-type")
       "<code>nil Slicey (slice [_] nil))</code>"]

      [:row {:title "Reify"}
       "<code>(</code>"
       (→clj "reify")
       "<code>Slicey (slice [at] ...))</code>"]

      `[:row {:title "Test"}
        ~@(map →clj ["satisfies?" "extends?"])]

      `[:row {:title "Other"}
        ~@(map →clj ["extend" "extend-protocol" "extenders"])]]]
    [:section {:title "Records (<a href=\"http://clojure.org/datatypes\">clojure.org/datatypes</a>)"}
     [:table {}
      [:row {:title "Define"}
       "<code>(</code>"
       (→clj "defrecord")
       "<code>Pair [h t])</code>"]

      [:row {:title "Access"}
       "<code>(:h (Pair. 1 2))</code> &rarr; <code>1</code>"]

      `[:row {:title "Create"}
        ~(map code ["Pair." "->Pair" "map->Pair"])]

      [:row {:title "Test"}
       (→clj "record?")]]]
    [:section {:title "Types (<a href=\"http://clojure.org/datatypes\">clojure.org/datatypes</a>)"}
     [:table {}
      [:row {:title "Define"}
       "<code>(</code>"
       (→clj "deftype")
       "<code>Pair [h t])</code>"]

      [:row {:title "Access"}
       "<code>(.h (Pair. 1 2))</code> &rarr; <code>1</code>"]

      `[:row {:title "Create"}
        ~(map code ["Pair." "->Pair"])]

      [:row {:title "With methods"}
       "<code>(</code>"
       (→clj "deftype")
       "<code>Pair [h t]<br>&nbsp;&nbsp;Object<br>&nbsp;&nbsp;(toString [this] (str \"<\" h \",\" t \">\")))</code>"]]]

    [:section {:title "Multimethods (<a href=\"http://clojure.org/multimethods\">clojure.org/multimethods</a>)"}
     [:table {}
      [:row {:title "Define"}
       "<code>(</code>"
       (→clj "defmulti")
       "<code>my-mm dispatch-fn)</code>"]

      [:row {:title "Method define"}
       "<code>(</code>"
       (→clj "defmethod")
       "<code>my-mm :dispatch-value [args] ...)</code>"]

      `[:row {:title "Dispatch"}
        ~@(map →clj ["get-method" "methods"])]

      `[:row {:title "Remove"}
        ~@(map →clj ["remove-method" "remove-all-methods"])]

      `[:row {:title "Prefer"}
        ~@(map →clj ["prefer-method" "prefers"])]

      `[:row {:title "Relation"}
        ~@(map →clj ["derive" "isa?" "parents" "ancestors" "descendants" "make-hierarchy"])]]]]))

(def macros
  (u/softref-cached
   [:box {:class "green grid-item"}
    [:section {:title "Macros"}
     [:table {}
      `[:row {:title "Create"}
        ~@(map →clj ["defmacro" "definline"])]

      `[:row {:title "Debug"}
        ~@(map →clj ["macroexpand-1" "macroexpand" "clojure.walk/macroexpand-all"])]

      `[:row {:title "Branch"}
        ~@(map →clj ["and" "or" "when" "when-not" "when-let"
                     "when-first" "if-not" "if-let" "cond" "condp"
                     "case"])
        [:br] "(1.6)"
        ~@(map →clj ["when-some" "if-some"])]

      `[:row {:title "Loop"}
        ~@(map →clj ["for" "doseq" "dotimes" "while"])]

      `[:row {:title "Arrange"}
        ~@(map →clj [".." "doto" "->" "->>"])
        [:br] "(1.5)" ~@(map →clj ["as->" "cond->" "cond->>" "some->" "some->>"])]

      `[:row {:title "Scope"}
        ~@(map →clj ["binding" "locking" "time" "with-in-str" "with-local-vars"
                     "with-open" "with-out-str" "with-precision" "with-redefs"
                     "with-redefs-fn"])]

      `[:row {:title "Lazy"}
        ~@(map →clj ["lazy-cat" "lazy-seq" "delay"])]

      `[:row {:title "Doc."}
        ~@(map →clj ["assert" "comment" "clojure.repl/doc"])]]]]))

(def vars
  (u/softref-cached
   [:box {:class "blue2 grid-item"}
    [:section {:title "Vars and global environment (<a href=\"http://clojure.org/reference/vars\">clojure.org/vars</a>)"}
     [:table {}
      `[:row {:title "Def variants"}
        ~@(map →clj ["def" "defn" "defn-" "definline" "defmacro" "defmethod"
                     "defmulti" "defonce" "defrecord"])]

      `[:row {:title "Interned vars"}
        ~@(map →clj ["declare" "intern" "binding" "find-var" "var"])]

      `[:row {:title "Var objects"}
        ~@(map →clj ["with-local-vars" "var-get" "var-set" "alter-var-root"
                     "var?" "bound?" "thread-bound?"])]

      `[:row {:title "Var validators"}
        ~@(map →clj ["set-validator!" "get-validator"])]]]]))

(def namespaces
  (u/softref-cached
   [:box {:class "yellow grid-item"}
    [:section {:title "Namespace"}
     [:table {}
      [:row {:title "Current"}
       (→clj "*ns*")]

      `[:row {:title "Create/Switch"}
        "(<a href=\"http://blog.8thlight.com/colin-jones/2010/12/05/clojure-libs-and-namespaces-require-use-import-and-ns.html\">tutorial</a>)"
        ~@(map →clj ["ns" "in-ns" "create-ns"])]

      `[:row {:title "Add"}
        ~@(map →clj ["alias" "def" "import" "intern" "refer"])]

      `[:row {:title "Find"}
        ~@(map →clj ["all-ns" "find-ns"])]

      `[:row {:title "Examine"}
        ~@(map →clj ["ns-name" "ns-aliases" "ns-map" "ns-interns" "ns-publics"
                     "ns-refers" "ns-imports"])]

      `[:row {:title "From symbol"}
        ~@(map →clj ["resolve" "ns-resolve" "namespace" "the-ns"])]

      `[:row {:title "Remove"}
        ~@(map →clj ["ns-unalias" "ns-unmap" "remove-ns"])]]]]))

(def loading
  (u/softref-cached
   [:box {:class "green grid-item"}
    [:section {:title "Loading"}
     [:table {}
      `[:row {:title "Load libs"}
        "(<a href=\"http://blog.8thlight.com/colin-jones/2010/12/05/clojure-libs-and-namespaces-require-use-import-and-ns.html\">tutorial</a>)"
        ~@(map →clj ["require" "use" "import" "refer"])]

      `[:row {:title "List loaded"}
        ~@(map →clj ["loaded-libs"])]

      `[:row {:title "Load misc"}
        ~@(map →clj ["load" "load-file" "load-reader" "load-string"])]]]]))

(def concurrency
  (u/softref-cached
   [:box {:class "magenta grid-item"}
    [:section {:title "Concurrency"}
     [:table {}
      `[:row {:title "Atoms"}
        ~@(map →clj ["atom" "swap!" "reset!" "compare-and-set!"])]

      `[:row {:title "Futures"}
        ~@(map →clj ["future" "future-call" "future-done?" "future-cancel"
                     "cancelled?" "future?"])]

      `[:row {:title "Threads"}
        ~@(map →clj ["bound-fn" "bound-fn*" "get-thread-bindings"
                     "get-thread-bindings" "push-thread-bindings"
                     "pop-thread-bindings" "thread-bound?"])]

      `[:row {:title "Misc"}
        ~@(map →clj ["locking" "pcalls" "pvalues" "pmap" "seque" "promise"
                     "deliver"])]]

     [:subsection {:title "Refs and Transactions (<a href=\"http://clojure.org/refs\">clojure.org/refs</a>)"}
      [:table {}
       `[:row {:title "Create"}
         ~@(map →clj ["ref"])]

       [:row {:title "Examine"}
        (→clj "deref")
        "(<code>@<var>form</var></code> &rarr; <code>(deref <var>form</var>)</code>)"]

       `[:row {:title "Transaction"}
         ~@(map →clj ["sync" "dosync" "io!"])]


       `[:row {:title "In transaction"}
         ~@(map →clj ["ensure" "ref-set" "alter" "commute"])]

       `[:row {:title "Validators"}
         ~@(map →clj ["set-validator!" "get-validator"])]

       `[:row {:title "History"}
         ~@(map →clj ["ref-history-count" "ref-max-history" "ref-min-history"])]]]

     [:subsection {:title "Agents and Asynchronous Actions (<a href=\"http://clojure.org/agents\">clojure.org/agents</a>)"}
      [:table {}
       [:row {:title "Create"}
        (→clj "agent")]

       [:row {:title "Examine"}
        (→clj "agent-error")]

       `[:row {:title "Change state"}
         ~@(map →clj ["send" "send-off" "restart-agent"])
         [:br] "(1.5)"
         ~@(map →clj ["send-via" "set-agent-send-executor!"
                      "set-agent-send-off-executor!"])]

       `[:row {:title "Block waiting"}
         ~@(map →clj ["await" "await-for"])]

       `[:row {:title "Ref validators"}
         ~@(map →clj ["set-validator!" "get-validator"])]

       `[:row {:title "Watchers"}
         ~@(map →clj ["add-watch" "remove-watch"])]

       `[:row {:title "Thread handling"}
         ~@(map →clj ["shutdown-agents"])]

       `[:row {:title "Error"}
         ~@(map →clj ["error-handler" "set-error-handler!" "error-mode"
                      "set-error-mode!"])]

       `[:row {:title "Misc"}
         ~@(map →clj ["*agent*" "release-pending-sends"])]]]]]))

;; FIXME: Clojurescript?
;; FIXME: ClojureCLJ?
(def interop
  (u/softref-cached
   [:box {:class "orange grid-item"
          :title "Java Interoperation (<a href=\"http://clojure.org/java_interop\">clojure.org/java_interop</a>)"}
    [:section {}
     [:table {}
      `[:row {:title "General"}
        ~@(map →clj [".." "doto" "new" "bean" "comparator" "enumeration-seq"
                     "import" "iterator-seq" "memfn" "set!" "class" "class?"
                     "bases" "supers" "type"])
        [:br]
        "<code>(Classname/staticMethod arg1 arg2)</code>" [:br]
        "<code>Classname/staticField</code>" [:br]
        "<code>(. classInstance instanceField)</code>" [:br]
        "<code>(. classInstance (instanceMethod arg1 arg2))</code>" [:br]
        "<code>(Classname.)</code>"]

      `[:row {:title "Cast"}
        ~@(map →clj ["boolean" "byte" "short" "char" "int" "long" "float"
                     "double" "bigdec" "bigint" "num" "cast" "biginteger"])]

      `[:row {:title "Exceptions"}
        ~@(map →clj ["throw" "try" "catch" "finally"
                     "clojure.repl/pst"])
        [:br] "(1.4)"
        ~@(map →clj ["ex-info" "ex-data"])]]

     [:subsection {:title "Arrays"}
      [:table {}
       `[:row {:title "Create"}
         ~@(map →clj (concat ["make-array" "to-array-2d" "aclone"]
                             (map #(str % "-array")
                                  ["object" "boolean" "byte" "short" "char" "int"
                                   "long" "float" "double" "into" "to"
                                   "into-array"])))]

       `[:row {:title "Use"}
         ~@(map →clj ["aget" "aset" "aset-boolean" "aset-byte" "aset-short"
                      "aset-char" "aset-int" "aset-long" "aset-float"
                      "aset-double" "alength" "amap" "areduce"])]

       `[:row {:title "Cast"}
         ~@(map →clj ["booleans" "bytes" "shorts" "chars" "ints" "longs" "floats"
                      "doubles"])]]]

     [:subsection {:title "Proxy"}
      [:table {}
       `[:row {:title "Create"}
         ~@(map →clj ["proxy" "get-proxy-class" "construct-proxy" "init-proxy"])]

       `[:row {:title "Misc"}
         ~@(map →clj ["proxy-mappings" "proxy-super" "update-proxy"])]]]]]))

(def msc
  (u/softref-cached
   [:box {:class "green2 grid-item"}
    [:section {:title "Other"}
     [:table {}
      `[:row {:title "XML"}
        ~@(map →clj ["clojure.xml/parse" "xml-seq"])]]]]))

;; The Cheatsheet
;;------------------------------------------------------------------------------

(def cheatsheet
  (u/softref-cached
   (layout ;; FIXME: is this a standard layout instance?
    (assoc (cfg/site-config)
           :page false
           :css ["/public/css/cheatsheet.css"
                 "/public/style.css"
                 "/public/jquery-ui.css"]
           :js  ["/public/jquery.js"
                 "/public/jquery-ui.js"
                 "/public/masonry.js"
                 "/public/cheatsheet.js"])

    [:nav {:class "search"}
     [:input {:type        "text"
              :id          "search"
              :placeholder "Type to search..."
              :autofocus   "autofocus"}]]
    [:div {:class "grid"
           :id    "cheatsheet"}
     [:div {:class "grid-sizer grid-item"}]
     (map render-fragment
          [
           (repl)
           (numbers)
           (functions)
           (abstractions)
           (macros)
           (vars)
           (namespaces)
           (loading)
           (concurrency)
           (interop)
           (msc)
           ])])))
