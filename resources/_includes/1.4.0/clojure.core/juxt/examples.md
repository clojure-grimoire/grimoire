### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Extract values from a map.

user=> ((juxt :a :b) {:a 1 :b 2 :c 3 :d 4})
[1 2]
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; "Explode" a value.

user=> ((juxt identity name) :keyword)
[:keyword "keyword"]


;; eg. to create a map:

user=> (into {} (map (juxt identity name) [:a :b :c :d]))
{:a "a" :b "b" :c "c" :d "d"}
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Get the first character and length of string

user=> ((juxt first count) "Clojure Rocks")
[\C 13]
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; sort list of maps by multiple values
user => (sort-by (juxt :a :b) [{:a 1 :b 3} {:a 1 :b 2} {:a 2 :b 1}]
[{:a 1 :b 2} {:a 1 :b 3} {:a 2 :b 1}]{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
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
{% endraw %}
{% endhighlight %}


