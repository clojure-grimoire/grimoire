(ns grimoire.web.views.api
  (:refer-clojure :exclude [ns-resolve])
  (:require [grimoire.api :as api]
            [grimoire.util :refer [succeed? result]]
            [grimoire.web.views
             :refer [site-config ns-version-index]]
            [grimoire.things :as t]
            [cheshire.core :refer [generate-string]]))

(defn fail [body]
  {:result :failure
   :body   body})

(defn succeed [body]
  {:result :success
   :body   body})

(defn edn-resp [data]
  {:status  200
   :headers {"Content-Type" "application/edn"}
   :body    (pr-str data)})

(defn json-resp [data]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (generate-string data)})

;; FIXME: This isn't gonna handle unknown request types worth shit
(def -tm
  {:application/json json-resp
   :application/edn  edn-resp})

(defn unknown-op
  "This function should yield a JSON error result indicating what the requested
  unsupported operation was and what the path on which it was invoked was."
  [type op thing]
  (-> {:op      op
      :path    (:uri thing)
      :message (str "Unknown op " op
                    " on target " (:uri thing))}
     fail
     ((-tm type))))

(declare list-groups
         ns-resolve
         group-ops
         artifact-ops
         version-ops
         namespace-ops
         def-ops)

(def root-ops
  {"groups"     #'list-groups
   "ns-resolve" #'ns-resolve})

(defn list-groups
  "Returns a success result representing the known groups."
  [type _]
  (try
    (-> (for [g (-> site-config
                  api/list-groups
                  result)]
         {:name     (:name g)
          :html     (str "/store/" (:uri g))
          :children (->> (for [op (keys group-ops)]
                         [op (str "/api/v0/" (:uri g) "?op=" op)])
                       (into {}))})
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(defn ns-resolve
  "Returns a success result representing the version and artifact in which a
  namespace is defined."
  [type params]
  (-> (if-let [ns (get params :ns)]
       (if-let [r (get ns-version-index ns)]
         (let [version-t  r
               artifact-t (:parent version-t)
               group-t    (:parent artifact-t)
               ns-t       (t/->Ns version-t ns)]
           (succeed {:namespace      ns
                     :version        (:name version-t)
                     :artifact       (:name artifact-t)
                     :group          (:name group-t)
                     :html           (str "/store/" (:uri ns-t))
                     :children       (->> (for [op (keys namespace-ops)]
                                          [op (str "/api/v0/" (:uri ns-t) "?op=" op)])
                                        (into {}))}))
         (fail "Unknown namespace :c"))
       (fail "ns paramenter not set!"))
     ((-tm type))))

(declare group-notes
         group-meta
         group-artifacts
         artifact-ops)

(def group-ops
  {"artifacts" #'group-artifacts
   "notes"     #'group-notes
   "meta"      #'group-meta})

(defn group-notes
  "Returns the notes, if any, for the target group."
  [type thing]
  (try
    (->> thing
       (api/read-notes site-config)
       result
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(defn group-meta
  "Returns the metadata, if any, for the target group."
  [type thing]
  (try
    (->> thing
       (api/read-meta site-config)
       result
       succeed
       ((-tm type)))
    
    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(defn group-artifacts
  "Returns the artifacts for the target group."
  [type group-thing]
  (try
    (-> (for [t (-> site-config
                  (api/list-artifacts group-thing)
                  result)]
         {:name     (:name t)
          :uri      (:uri t)
          :html     (str "/store/" (:uri t))
          :children (->> (for [op (keys artifact-ops)]
                         [op (str "/api/v0/" (:uri t) "?op=" op)])
                       (into {}))})
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(declare artifact-versions
         namespace-ops)

(def artifact-ops
  {"versions" #'artifact-versions
   "notes"    #'group-notes
   "meta"     #'group-meta})

(defn artifact-versions
  "Returns the versions for the target artifact."
  [type artifact-thing]
  (try
    (-> (for [t (-> site-config
                  (api/list-versions artifact-thing)
                  result)]
         {:name     (:name t)
          :uri      (:uri t)
          :html     (str "/store/" (:uri t))
          :children (->> (for [op (keys version-ops)]
                         [op (str "/api/v0/" (:uri t) "?op=" op)])
                       (into {}))})
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(declare version-namespaces)

(def version-ops
  {"namespaces" #'version-namespaces
   "notes"      #'group-notes
   "meta"       #'group-meta})

(defn version-namespaces
  "Returns a Succeed of the namespaces in the target version."
  [type version-thing]
  (try
    (-> (for [t (-> site-config
                  (api/list-namespaces version-thing)
                  result)]
         {:name     (:name t)
          :uri      (:uri t)
          :html     (str "/store/" (:uri t))
          :children (->> (for [op (keys namespace-ops)]
                         [op (str "/api/v0/" (:uri t) "?op=" op)])
                       (into {}))})
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(declare def-ops
         namespace-search)

(def namespace-ops
  {"all"       (partial #'namespace-search identity)
   "macros"    (partial #'namespace-search #{:macro})
   "vars"      (partial #'namespace-search #{:var})
   "fns"       (partial #'namespace-search #{:fn})
   "specials"  (partial #'namespace-search #{:special})
   "sentinels" (partial #'namespace-search #{:sentinel})
   "notes"     #'group-notes
   "meta"      #'group-meta})

;; FIXME: memoize/cache this shit b/c slow as fuck
(defn namespace-search
  "Returns a Succeed listing the defs of the target namespace and the supported
  operations thereon."
  [filter type ns-thing]
  (try
    (-> (for [t     (-> site-config
                      (api/list-defs ns-thing)
                      result)
             :let  [meta (api/read-meta site-config t)]
             :when (filter (get t :type :fn))]
         {:name     (:name t)
          :uri      (:uri t)
          :html     (str "/store/" (:uri t))
          :children (->> (for [op (keys def-ops)]
                         [op (str "/api/v0/" (:uri t) "?op=" op)])
                       (into {}))})
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(declare def-examples
         def-related)

(def def-ops
  {"notes"    #'group-notes
   "meta"     #'group-meta
   "examples" #'def-examples
   "related"  #'def-related})

(defn def-examples
  "Returns a Succeed of examples for the given def, if any exist in the
  datastore."
  [type def-thing]
  (try
    (->> def-thing
       (api/read-examples site-config)
       result
       (map second)
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))

(defn def-related
  "Returns a Succeed of related, namespace qualified symbols for the given def
  encoded as strings if any exist in the datastore."
  [type def-thing]
  (try
    (->> (for [t     (-> site-config
                      (api/read-related def-thing)
                      result)]
         {:name     (:name t)
          :uri      (:uri t)
          :html     (str "/store/" (:uri t))
          :children (->> (for [op (keys def-ops)]
                         [op (str "/api/v0/" (:uri t) "?op=" op)])
                       (into {}))})
       succeed
       ((-tm type)))

    (catch Exception e
      (-> (.getMessage e)
         fail
         ((-tm type))))))
