(ns grimoire.web.views.content.cheatsheet
  (:refer-clojure :exclude [munge])
  (:require [grimoire.web.views :refer :all]
            [clojure.string :as string]
            [grimoire.api :as api]
            [grimoire.api.web :as web]
            [grimoire.either :refer [succeed? result]]
            [grimoire.things :as t]
            [grimoire.web.layout :refer [layout]]
            [grimoire.web.util :as wutil]
            [grimoire.util :refer [munge]]
            [grimoire.web.config :as cfg]
            [grimoire.github :as gh]
            [simpledb.core :as sdb]))

(defn common-prefix [prefix & syms]
  )

(defn →thing [s]
  {:pre [(string? s)]}
  (let [t (t/url-path->thing s)]
    (web/make-html-url (cfg/web-config) t)))

(defn →
  ([s]
   [:a {:href s} s])

  ([a t]
   [:a {:href a} t]))

(def ^:dynamic *prefix* nil)

(defn →clj
  ([s]
   {:pre [(string? s)]}
   (if (.startsWith s "clojure.")
     (→ (→thing (str "org.clojure/clojure/latest/clj/" s)) s)
     (let [s (str *prefix* s)]
       (→ (→thing (str "org.clojure/clojure/latest/clj/clojure.core/" (munge s))) s))))

  ([l s]
   (→ (→clj l) s)))

(defn code [& forms]
  `[:code {} ~@forms])

(defn table [& forms]
  `[:table {} ~@forms])

(defmacro with-common-prefix [p & forms]
  `(with-bindings [*prefix* ~p]
     []))

(def clojure.repl
  [:box {:style "green"}
   [:section {:title "Documentation"}
    [:table {:title "clojure.repl/"}
     [:row {}
      (→clj "clojure.repl/doc" "doc")
      (→clj "clojure.repl/find-doc" "find-doc")
      (→clj "clojure.repl/apropos" "apropos")
      (→clj "clojure.repl/source" "source")
      (→clj "clojure.repl/pst" "pst")
      (→clj "clojure.java.javadoc/javadoc")]]]])

(def clojure.numbers
  [:subsection {:title "Numbers"}
   [:table {}
    [:row {:title "Literals"}
     ;; Longs
     (→ "http://docs.oracle.com/javase/7/docs/api/java/lang/Long.html" "Long:")
     "dec:"    (code "7") ","
     "hex:"    (code "0xff") ","
     "oct:"    (code "017") ","
     "binary:" (code "2r1011") ","
     "base 36" (code "36rCRAZY")
     "BigInt:" (code "7N")
     "Ratio:"  (code "-22/7")

     ;; Doubles
     (→ "http://docs.oracle.com/javase/7/docs/api/java/lang/Double.html" "Double:")
     (code "2.78") "," (code "-1.2e-5")

     ;; BigDecimals
     (→ "http://docs.oracle.com/javase/7/docs/api/java/math/BigDecimal.html" "BigDecimal:")
     (code "4.2M") "," (code "5M")]
    
    `[:row {:title "Arithmetic"}
      ~@(map →clj ["+" "-" "*" "/" "quot" "rem" "mod" "inc" "dec" "max" "min"
                   "+'" "-'" "*'" "inc'" "dec'"])]
    
    `[:row {:title "Compare"}
      ~@(map →clj ["==" "<" ">" "<=" ">=" "compare"])]
    
    `[:row {:title "Bitwise"}
      ~@(map →clj ["bit-and" "bit-or" "bit-xor" "bit-not" "bit-flip" "bit-set"
                   "bit-shift-right" "bit-shift-left" "bit-and-not" "bit-clear"
                   "bit-test"])
      
      "(1.6)" ~(→clj "bit-unsigned-bit-shift-right")
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
             ["add" "dec" "inc" "multiply" "negate" "subtract"])]]])

(defn cheatsheet []
  (layout ;; FIXME: is this a standard layout instance?
   (cfg/site-config)
   [:h1 {:class "page-title"} "FIXME"]
   ))
