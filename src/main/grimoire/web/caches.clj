(ns grimoire.web.caches
  (:require [grimoire
             [api :as api]
             [either :refer [result]]
             [things :as t]]
            [grimoire.web
             [config :refer [lib-grim-config]]
             [util :refer [maybe]]]))

(defn -clj-versions []
  (let [*cfg* (lib-grim-config)
        γ     (fn [f coll] (maybe (f *cfg* coll)))
        a     (t/path->thing "org.clojure/clojure")]
    (for [v (γ api/list-versions a)]
      (t/thing->name v))))

(def clj-versions
  (memoize -clj-versions))

(defn -ns-version-index []
  (let [*cfg* (lib-grim-config)
        γ     (fn [f coll] (maybe (f *cfg* coll)))]
    (->> (for [groupid   (result (api/list-groups *cfg*))
               artifact  (γ api/list-artifacts groupid)
               :let      [version  (first (γ api/list-versions artifact))
                          platform (->> (γ api/list-platforms version)
                                        (sort-by t/thing->name)
                                        first)]
               namespace (γ api/list-namespaces platform)]
           [(t/thing->name namespace) version])
         (into {}))))
