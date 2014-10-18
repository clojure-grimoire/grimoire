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
(defrecord GroupHandle [handle name])

(defn list-groups [{{docs :docs} :datastore}]
  {:pre [(or (instance? java.io.File docs)
             (instance? java.lang.String docs))]}

  (let [f (io/file docs)]
    (when (and (.exists f)
               (.isDirectory f))
      (for [f' (.listFiles f)
            :when (.isDirectory f')
            :when (= "group" (slurp (io/file f' ":type")))]
        (->GroupHandle f' (. f' (getName)))))))

;; Artifact stuff
;;--------------------------------------------------------------------
(defrecord ArtifactHandle [handle parent name])

(defn list-artifacts [config group]
  {:pre [(instance? GroupHandle group)]}

  (when-let [f (:handle group)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')
          :when (= "artifact" (slurp (io/file f' ":type")))]
      (->ArtifactHandle f' group (. f' (getName))))))

;; Version stuff
;;--------------------------------------------------------------------
(defrecord VersionHandle [handle parent name])

(defn list-versions [config artifact]
  {:pre [(instance? ArtifactHandle artifact)]}

  (when-let [f (:handle artifact)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')]
      (->VersionHandle f' artifact (. f' (getName))))))

(defn- list-type-filtered [type ctor config parent]
  (when-let [f (:handle parent)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')
          :let  [typef (io/file f' ":type")]
          :when (.exists typef)
          :when (= type (slurp typef))]
      (ctor f' parent (. f' (getName))))))

;; Namespace stuff
;;--------------------------------------------------------------------
(defrecord NamespaceHandle [handle parent name])

(defn list-namespaces [config version]
  {:pre [(instance? VersionHandle version)]}

  (list-type-filtered "ns" ->NamespaceHandle config version))

;; Package stuff
;;--------------------------------------------------------------------
(defrecord PackageHandle [handle parent name])

(defn list-packages [config version]
  {:pre [(instance? VersionHandle version)]}

  (list-type-filtered "package" ->PackageHandle config version))

;; Def stuff
;;--------------------------------------------------------------------
;; Type one of #{:symbol :var :fn :macro :class}
(defrecord DefHandle [handle parent name type])

(defn- list-ns-contents [ctor type config parent]
  (when-let [f (:handle parent)]
    (for [f'    (.listFiles f)
          :when (.isDirectory f')
          :let  [typef (io/file f' ":type")]
          :when (.exists typef)
          :when (= type (slurp typef))]
      (ctor f' parent (. f' (getName)) type))))

(defn list-vars [config namespace]
  {:pre [(instance? NamespaceHandle namespace)]}
  (list-ns-contents ->DefHandle "var" config namespace))

(defn list-fns [config namespace]
  {:pre [(instance? NamespaceHandle namespace)]}
  (list-ns-contents ->DefHandle "fn" config namespace))

(defn list-macros [config namespace]
  {:pre [(instance? NamespaceHandle namespace)]}
  (list-ns-contents ->DefHandle "macro" config namespace))

(defn list-symbols [config namespace]
  {:pre [(instance? NamespaceHandle namespace)]}
  (list-ns-contents ->DefHandle "symbol" config namespace))

;; Class stuff
;;--------------------------------------------------------------------
(defrecord ClassHandle [handle parent name])

(defn list-classes [config namespace]
  {:pre [(instance? NamespaceHandle namespace)]}
  (list-type-filtered ->ClassHandle "class" config namespace))

(defn thing->path [thing]
  (->> thing
       (iterate :parent)
       (take-while identity)
       (reverse)
       (map :name)
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
