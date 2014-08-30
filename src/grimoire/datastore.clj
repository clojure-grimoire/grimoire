(ns grimoire.datastore
  (:refer-clojure :exclude [replace])
  (:require [clojure.java.io :as io]
            [clojure.string :refer [lower-case upper-case replace]]
            [me.raynes.fs :as fs]
            [grimoire.util :refer :all]))

;; Intended file structure output
;;--------------------------------------------------------------------
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/release-notes.md
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/ns-notes.md
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/name.txt
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/type.txt
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/arities.txt
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/docstring.md
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/extended-docstring.md
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/source.clj
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/related.txt
;; /datastore/$GROUPID/$ARTIFACT/$VERSION/ns/$NAMESPACE/sym/$SYMBOL/ex/$EXAMPLE_ID.clj

(defn dot [d & dirs]
  {:pre [(string? d)
         (every? string? dirs)]}
  (->> dirs (interpose \/) str
     (io/file d)))

(defn file->ns [fpath]
  (-> fpath
      (replace #".clj$" "")
      (replace #"_" "-")
      (replace #"/" ".")))

(defn write-docs
  [groupid artifactid version
   {:keys [namespace raw-symbol symbol type arglists doc src examples related]}]
  (let [sym-dir (->> ["datastore" groupid artifactid version "ns" namespace "sym" raw-symbol]
                   (interpose \/)
                   (dot "resources"))]
    (fs/mkdirs sym-dir)

    ;; write the name file
    (let [name-file (dot sym-dir "name.txt")]
      (when-not (fs/exists? name-file)
        (spit name-file raw-symbol)))

    ;; write type file
    (let [type-file (dot sym-dir "type.txt")]
      (when-not (fs/exists? type-file)
        (spit type-file type)))

    ;; write arities file
    (let [arities-file (dot sym-dir "arities.txt")]
      (when-not (fs/exists? arities-file)
        (doseq [l arglists]
          (spit arities-file
                (str l "\n")
                :append true))))

    ;; write docstring file
    (let [doc-file (dot sym-dir "docstring.md")]
      (when-not (fs/exists? doc-file)
        (spit doc-file doc)))

    ;; touch extended docstring
    (let [extended-doc-file (dot sym-dir "extended-docstring.md")]
      (when-not (fs/exists? extended-doc-file)
        (spit extended-doc-file "No user documentation! Care to add some?\n")))

    ;; write source file
    (when src
      ;; write source file
      (let [src-file (dot sym-dir "source.clj")]
        (when-not (fs/exists? src-file)
          (spit src-file src))))

    ;; write related file
    (let [related-file (dot sym-dir "related.txt")]
      (when-not (fs/exists? related-file)
        (when related
          (doseq [{:keys [file name] :as el} @related]
            (let [file (or file "clojure/core.clj")]
              (spit related-file
                    (str (file->ns file) "/" name "\n")
                    :append true)))
          (spit related-file ""))))

    ;; write examples from clojuredocs
    (let [ex-dir (dot sym-dir "ex")]
      (when-not (fs/exists? ex-dir)
        (.mkdir ex-dir)

        (when examples
          (doseq [{:keys [body] :as e} @examples]
            (let [fname (str (Math/abs (hash body)) ".clj")
                  f     (dot ex-dir fname)]
              (spit f (-> body (replace #"</?pre>" ""))))))))))
