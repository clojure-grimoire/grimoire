{% include 1.5.0/clojure.core/reduce_DASH_kv/examples.md %}

Example: Use reduce-kv to invert a map

    user=> (reduce-kv (fn [accum k v] (conj accum {v k})) {} {:a 1 :b 2 :c 3 :d 3})
    {1 :a, 2 :b, 3 :d}

[Please add more examples!](https://github.com/arrdem/grimoire/edit/master/_includes/1.6.0/clojure.core/reduce_DASH_kv/examples.md)
