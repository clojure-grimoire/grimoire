(ns grimoire.web.views.api
  (:require [grimoire.api :as api]
            [grimoire.api.fs]
            [grimoire.api.fs.read]
            [grimoire.web.views :refer [site-config]]
            [cheshire.core :refer [generate-string]]))

(defn fail [body]
  (-> {:result :failure
      :body   body}))

(defn succeed [body]
  (-> {:result :success
      :body   body}))

(defn edn-resp [data]
  {:status  200
   :headers {"Content-Type" "application/edn"}
   :body    (pr-str data)})

(defn json-resp [data]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (generate-string data)})

(def -tm
  {:application/json json-resp
   :application/edn  edn-resp})

(defn unknown-op
  "This function should yield a JSON error result indicating what the requested
  unsupported operation was and what the path on which it was invoked was."
  [thing op]
  (-> {:op      op
      :path    (:uri thing)
      :message (str "Unknown op " op
                    " on target " (:uri thing))}
    fail))

(declare list-groups
         group-ops
         artifact-ops
         version-ops
         ns-ops
         def-ops)

(def root-ops
  {"groups" list-groups})

(defn list-groups
  "Returns a success result representing the "
  [type _]
  (-> (for [g (api/list-groups site-config)]
       {:name (:name g)
        :html (str "/store/" (:uri g))
        :children (->> (for [op (keys group-ops)]
                       [op (str "/api/v0/" (:uri g) "?op=" op)])
                    (into {}))})
    succeed
    ((-tm type))))

(declare group-notes
         group-meta
         group-artifacts
         artifact-ops)

(def group-ops
  {"artifacts" group-artifacts
   "notes"     group-notes
   "meta"      group-meta})

(defn group-notes
  "Returns the notes, if any, for the target group."
  [type thing]
  (->> thing
    (api/read-notes site-config)
    succeed
    ((-tm type))))

(defn group-meta
  "Returns the metadata, if any, for the target group."
  [type thing]
  (->> thing
    (api/read-meta site-config)
    succeed
    ((-tm type))))

(defn group-artifacts
  "Returns the artifacts for the target group."
  [type group-thing]
  (-> (for [t (api/list-artifacts site-config group-thing)]
       {:name (:name t)
        :html (str "/store/" (:uri t))
        :children (->> (for [op (keys artifact-ops)]
                       [op (str "/api/v0/" (:uri t) "?op=" op)])
                    (into {}))})
    succeed
    ((-tm type))))

(declare artifact-versions
         namespace-ops)

(def artifact-ops
  {"versions" artifact-versions
   "notes"    group-notes
   "meta"     group-meta})

(defn artifact-versions
  "Returns the versions for the target artifact."
  [type artifact-thing]
  (-> (for [t (api/list-versions site-config artifact-thing)]
       {:name (:name t)
        :html (str "/store/" (:uri t))
        :children (->> (for [op (keys version-ops)]
                       [op (str "/api/v0/" (:uri t) "?op=" op)])
                    (into {}))})
    succeed
    ((-tm type))))

(declare version-namespaces)

(def version-ops
  {"namespaces" version-namespaces
   "notes"      group-notes
   "meta"       group-meta})

(defn version-namespaces
  "Returns a Succeed of the namespaces in the target version."
  [type version-thing]
  (-> (for [t (api/list-namespaces site-config version-thing)]
       {:name (:name t)
        :html (str "/store/" (:uri t))
        :children (->> (for [op (keys namespace-ops)]
                       [op (str "/api/v0/" (:uri t) "?op=" op)])
                    (into {}))})
    succeed
    ((-tm type))))

(declare def-ops
         namespace-search)

(def namespace-ops
  {"all"       (partial namespace-search identity)
   "macros"    (partial namespace-search #{:macro})
   "vars"      (partial namespace-search #{:var})
   "fns"       (partial namespace-search #{:fn})
   "specials"  (partial namespace-search #{:special})
   "sentinels" (partial namespace-search #{:sentinel})
   "notes"     group-notes
   "meta"      group-meta})

;; FIXME: memoize/cache this shit b/c slow as fuck
(defn namespace-search
  "Returns a Succeed listing the defs of the target namespace and the supported
  operations thereon."
  [filter type ns-thing]
  (-> (for [t     (api/list-defs site-config ns-thing)
           :let  [meta (api/read-meta site-config t)]
           :when (filter (get t :type :fn))]
       {:name (:name t)
        :html (str "/store/" (:uri t))
        :children (->> (for [op (keys def-ops)]
                       [op (str "/api/v0/" (:uri t) "?op=" op)])
                    (into {}))})
    succeed
    ((-tm type))))

(declare def-examples)

(def def-ops
  {"notes"    group-notes
   "meta"     group-meta
   "examples" def-examples})

(defn def-examples
  "Returns a Succeed of examples for the given def, if any exist in the
  datastore."
  [type def-thing]
  (->> def-thing
    (api/read-examples site-config)
    (map second)
    succeed
    ((-tm type))))
