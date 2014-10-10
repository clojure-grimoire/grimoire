;;example of removing namespaces from all keys in a nested data structure
(def thing {:page/tags [{:tag/category "lslsls"}]})
(postwalk #(if(keyword? %)(keyword (name %)) %) thing)
{:tags [{:category "lslsls"}]}