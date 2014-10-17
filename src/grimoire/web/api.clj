(ns grimoire.web.api
  "This namespace serves to implement an abstraction layer for looking
  up examples, symbols, namespaces and artifacts as values without
  regard to the implementation of the datastore. Ports of Grimoire to
  different datastores should only need to tweak this namespace.")


;; Group stuff
;;--------------------------------------------------------------------
(defrecord Group [name])

(defn list-groups []
  {:pre []}
  )

;; Artifact stuff
;;--------------------------------------------------------------------
(defrecord Artifact [group artifact])

(defn list-artifacts [group]
  {:pre []}
  )

;; Version stuff
;;--------------------------------------------------------------------
(defrecord Version [group artifact version])

(defn list-versions [artifact]
  {:pre []}
  )

;; Namespace stuff
;;--------------------------------------------------------------------
(defrecord Namespace [group artifact version namespace])

(defn list-namespaces [version]
  {:pre []}
  )

;; Class stuff
;;--------------------------------------------------------------------
(defrecord Class [group artifact version class])

(defn list-classes [version]
  {:pre []}
  )

;; Def stuff
;;--------------------------------------------------------------------
;; Type one of #{:symbol :var :fn :macro}

(defrecord Def [group artifact version namespace class type])

(defn list-symbols [namespace]
  {:pre []}
  )

(defn list-vars [namespace]
  {:pre []}
  )

(defn list-fns [namespace]
  {:pre []}
  )

(defn list-macros [namespace]
  {:pre []}
  )

;; Examples
;;--------------------------------------------------------------------
(defn list-examples [thing]
  ;; group | artifact | version | namespace | symbol | macro | class | var
  {:pre []}
  )

;; Notes
;;--------------------------------------------------------------------
(defn notes [thing]
  {:pre []}
  )
