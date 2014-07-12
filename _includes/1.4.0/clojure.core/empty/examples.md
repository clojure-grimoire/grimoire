### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (empty [1 2 3])
[]
user=> (empty (list 1 2 3))
()
user=> (map empty [[\a \b] {1 2} (range 4)])
([] {} ())
user=> (swap! (atom (range 10)) empty)
(){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; The output will not necessarily be of the same JVM class as the input
user=> (class (seq [1]))
clojure.lang.PersistentVector$ChunkedSeq
user=> (class (empty (seq [1])))
clojure.lang.PersistentList$EmptyList
{% endraw %}
{% endhighlight %}


