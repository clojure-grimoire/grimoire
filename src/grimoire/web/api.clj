(ns grimoire.web.api
  "This namespace serves to implement an abstraction layer for looking
  up examples, symbols, namespaces and artifacts as values without
  regard to the implementation of the datastore. Ports of Grimoire to
  different datastores should only need to tweak this namespace."
  (:require [grimoire.web.util :as util]
            [clojure.java.io :as io]))

(defmacro maybe-file [& forms]
  `(let [f (apply io/file forms)]
     (when (.exists f) f)))

;; Group stuff
;;--------------------------------------------------------------------
(defrecord Group [handle name])

(defn list-groups [{{docs :docs} :datastore}]
  (let [f (io/file docs)]
    (when (and (.exists f)
               (.isDirectory f))
      (for [f' (.listFiles f)
            :when (.isDirectory f')]
        (->Group f (.name f'))))))

;; Artifact stuff
;;--------------------------------------------------------------------
(defrecord Artifact [handle parent name])

(defn list-artifacts [config group]
  (when-let [f (:handle group)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')]
      (->Artifact f' group (.name f)))))

;; Version stuff
;;--------------------------------------------------------------------
(defrecord Version [handle parent name])

(defn list-versions [config artifact]
  (when-let [f (:handle artifact)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')]
      (->Version f' artifact (.name f)))))


(defn list-type-filtered [type ctor config version]
  (when-let [f (:handle version)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')
          :let  [typef (io/file f' "type")]
          :when (.exists typef)
          :when (= type (slurp typef))]
      (ctor f' version (.name f)))))

;; Namespace stuff
;;--------------------------------------------------------------------
(defrecord Namespace [handle parent name])

(def list-namespaces
  (partial list-type-filtered "ns" ->Namespace))

;; Package stuff
;;--------------------------------------------------------------------
(defrecord Package [handle parent name])

(def list-packages
  (partial list-type-filtered "package" ->Package))

;; Def stuff
;;--------------------------------------------------------------------
;; Type one of #{:symbol :var :fn :macro :class}
(defrecord Def [handle parent name type])

(defn- list-ns-contents [ctor type config parent]
  (when-let [f (:handle parent)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')
          :let  [typef (io/file f' "type")]
          :when (.exists typef)
          :when (= type (slurp typef))]
      (ctor f' version (.name f) type))))

(def list-vars
  (partial list-ns-contents ->Def "var"))

(def list-fns
  (partial list-ns-contents ->Def "fn"))

(def list-macros
  (partial list-ns-contents ->Def "macro"))

(def list-special
  (partial list-ns-contents ->Def "special"))

;; Class stuff
;;--------------------------------------------------------------------
(defrecord Class [handle parent name])

(def list-classes
  (partial list-type-filtered "class" ->Class))

(defn thing->path [thing]
  (->> thing
       (iterate :parent)
       (take-while identity)
       (reverse)
       (map (comp util/my-munge :name))
       (interpose "/")
       (apply str)))

;; Examples
;;--------------------------------------------------------------------
(defn list-examples [config thing]
  ;; group | artifact | version | namespace | symbol | macro | class | var
  {:pre []}
  )

;; Notes
;;--------------------------------------------------------------------
(defn notes [config thing]
  {:pre []}
  )
