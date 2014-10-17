(ns grimoire.web.api
  "This namespace serves to implement an abstraction layer for looking
  up examples, symbols, namespaces and artifacts as values without
  regard to the implementation of the datastore. Ports of Grimoire to
  different datastores should only need to tweak this namespace.")


;; Group stuff
;;--------------------------------------------------------------------
(defrecord Group [name])

(defn list-groups [config]
  {:pre []}
  )

;; Artifact stuff
;;--------------------------------------------------------------------
(defrecord Artifact [group artifact])

(defn list-artifacts [config group]
  {:pre []}
  )

;; Version stuff
;;--------------------------------------------------------------------
(defrecord Version [group artifact version])

(defn list-versions [config artifact]
  {:pre []}
  )

;; Namespace stuff
;;--------------------------------------------------------------------
(defrecord Namespace [group artifact version namespace])

(defn list-namespaces [config version]
  {:pre []}
  )

;; Class stuff
;;--------------------------------------------------------------------
(defrecord Class [group artifact version class])

(defn list-classes [config version]
  {:pre []}
  )

;; Def stuff
;;--------------------------------------------------------------------
;; Type one of #{:symbol :var :fn :macro}

(defrecord Def [group artifact version namespace class type])

(defn list-symbols [config namespace]
  {:pre []}
  )

(defn list-vars [config namespace]
  {:pre []}
  )

(defn list-fns [config namespace]
  {:pre []}
  )

(defn list-macros [config namespace]
  {:pre []}
  )

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
