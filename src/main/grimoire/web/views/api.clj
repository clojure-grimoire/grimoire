(ns grimoire.web.views.api
  (:refer-clojure :exclude [ns-resolve])
  (:require [grimoire.api :as api]
            [grimoire.api.web :as web]
            [grimoire.either
             :refer [succeed? result]]
            [grimoire.web.views
             :refer [ns-version-index]]
            [grimoire.web.config
             :refer [lib-grim-config
                     site-config
                     web-config]]
            [grimoire.things :as t]
            [cheshire.core
             :refer [generate-string]]))

;; Helpers

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

;; Content implementations

(defn unknown-op
  "This function should yield a JSON error result indicating what the requested
  unsupported operation was and what the path on which it was invoked was."
  [type op thing]
  (-> {:op      op
       :path    (t/thing->path thing)
       :message (str "Unknown op " op
                     " on target " (t/thing->path thing))}
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
  (let [*g-cfg* (lib-grim-config)
        *w-cfg* (web-config)]
    (try
      (-> (for [g  (result (api/list-groups *g-cfg*))]
            {:name     (t/thing->name g)
             :html     (web/make-html-url *w-cfg* g)
             :children (->> (for [op (keys group-ops)]
                              [op (web/make-api-url *w-cfg* g op)])
                            (into {}))})
          succeed
          ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))

(defn ns-resolve
  "Returns a success result representing the version and artifact in which a
  namespace is defined."
  [type params]
  (let [*g-cfg* (lib-grim-config)
        *w-cfg* (web-config)]
    (-> (if-let [ns (get params :ns)]
          (if-let [r (get (ns-version-index) ns)]
            (let [version-t  r
                  artifact-t (t/thing->artifact version-t)
                  group-t    (t/thing->group artifact-t)
                  ns-t       (t/->Ns version-t ns)]
              (succeed {:namespace ns
                        :version   (t/thing->name version-t)
                        :artifact  (t/thing->name artifact-t)
                        :group     (t/thing->name group-t)
                        :html      (web/make-html-url *w-cfg* ns-t)
                        :children  (->> (for [op (keys namespace-ops)]
                                          [op (web/make-api-url *w-cfg* ns-t op)])
                                        (into {}))}))
            (fail "Unknown namespace :c"))
          (fail "ns paramenter not set!"))
        ((-tm type)))))

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
         (api/read-notes (lib-grim-config))
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
         (api/read-meta (lib-grim-config))
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
  (let [*w-cfg* (web-config)
        *g-cfg* (lib-grim-config)]
    (try
      (-> (for [t (result (api/list-artifacts *g-cfg* group-thing))]
            {:name     (t/thing->name t)
             :url      (t/thing->path t)
             :html     (web/make-html-url *w-cfg* t)
             :children (->> (for [op (keys artifact-ops)]
                              [op (web/make-api-url *w-cfg* t op)])
                            (into {}))})
          succeed
          ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))

(declare artifact-versions
         namespace-ops)

(def artifact-ops
  {"versions" #'artifact-versions
   "notes"    #'group-notes
   "meta"     #'group-meta})

(defn artifact-versions
  "Returns the versions for the target artifact."
  [type artifact-thing]
  (let [*w-cfg* (web-config)
        *g-cfg* (lib-grim-config)]
    (try
      (-> (for [t (result (api/list-versions *g-cfg* artifact-thing))]
            {:name     (t/thing->name t)
             :url      (t/thing->path t)
             :html     (web/make-html-url *w-cfg* t)
             :children (->> (for [op (keys version-ops)]
                              [op (web/make-api-url *w-cfg* t op)])
                            (into {}))})
          succeed
          ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))

(declare version-platforms
         platform-ops)

(def version-ops
  {"platforms" #'version-platforms
   "notes"     #'group-notes
   "meta"      #'group-meta})

(defn version-platforms
  "Returns a Succeed of the platforms in the target version."
  [type group-thing]
  (let [*g-cfg* (lib-grim-config)
        *w-cfg* (web-config)]
    (try
      (-> (for [t (result (api/list-platforms *g-cfg* group-thing))]
            {:name     (t/thing->name t)
             :url      (t/thing->path t)
             :html     (web/make-html-url *w-cfg* t)
             :children (->> (for [op (keys platform-ops)]
                              [op (web/make-api-url *w-cfg* t op)])
                            (into {}))})
          succeed
          ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))

(declare platform-namespaces)

(def platform-ops
  {"namespaces" #'platform-namespaces
   "notes"      #'group-notes
   "meta"       #'group-meta})

(defn platform-namespaces
  "Returns a Succeed of the namespaces in the target platform."
  [type platform-thing]
  (let [*g-cfg* (lib-grim-config)
        *w-cfg* (web-config)]
    (try
      (-> (for [t (result (api/list-namespaces *g-cfg* platform-thing))]
            {:name     (t/thing->name t)
             :url      (t/thing->path t)
             :html     (web/make-html-url *w-cfg* t)
             :children (->> (for [op (keys namespace-ops)]
                              [op (web/make-api-url *w-cfg* t op)])
                            (into {}))})
          succeed
          ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))

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
  [filter-pred type ns-thing]
  (let [*g-cfg* (lib-grim-config)
        *w-cfg* (web-config)]
    (try
      (-> (for [t     (result (api/list-defs *g-cfg* ns-thing))
                :let  [meta (api/read-meta *g-cfg* t)]
                :when (filter-pred (get t :type :fn))]
            {:name     (t/thing->name t)
             :url      (t/thing->path t)
             :html     (web/make-html-url *w-cfg* t)
             :children (->> (for [op (keys def-ops)]
                              [op (web/make-api-url *w-cfg* t op)])
                            (into {}))})
          succeed
          ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))

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
  (let [*g-cfg* (lib-grim-config)]
    (try
      (->> def-thing
           (api/list-examples *g-cfg*)
           result
           (map (comp result (partial api/read-example *g-cfg*)))
           succeed
           ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))

(defn def-related
  "Returns a Succeed of related, namespace qualified symbols for the given def
  encoded as strings if any exist in the datastore."
  [type def-thing]
  (let [*g-cfg* (lib-grim-config)
        *w-cfg* (web-config)]
    (try
      (->> (for [t (result (api/list-related *g-cfg* def-thing))]
             {:name     (t/thing->name t)
              :url      (t/thing->path t)
              :html     (web/make-html-url *w-cfg* t)
              :children (->> (for [op (keys def-ops)]
                               [op (web/make-api-url *w-cfg* t op)])
                             (into {}))})
           succeed
           ((-tm type)))

      (catch Exception e
        (-> (.getMessage e)
            fail
            ((-tm type)))))))
