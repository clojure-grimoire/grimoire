;; Create lookup maps via a specific key

user=> (defn index-by [coll key-fn]
         (into {} (map (juxt key-fn identity) coll)))
#'user/index-by
user=> (index-by [{:id 1 :name "foo"} 
                  {:id 2 :name "bar"} 
                  {:id 3 :name "baz"}] :id)
{1 {:name "foo", :id 1}, 2 {:name "bar", :id 2}, 3 {:name "baz", :id 3}}

user=> (index-by [{:id 1 :name "foo"} 
                  {:id 2 :name "bar"} 
                  {:id 3 :name "baz"}] :name)
{"foo" {:name "foo", :id 1}, "bar" {:name "bar", :id 2}, "baz" {:name "baz", :id 3}}
