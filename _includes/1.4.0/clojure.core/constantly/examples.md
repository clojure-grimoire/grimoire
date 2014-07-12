### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def boring (constantly 10))
#'user/boring

user=> (boring 1 2 3)
10
user=> (boring)
10
user=> (boring "Is anybody home?")
10
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; A really goofy way to find the size of a collection
user=> (reduce + (map (constantly 1) [:a :b :c]))
3{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; constantly returns a function which always returns the same value
(map (constantly 9) [1 2 3])
user=> (9 9 9)

(map (constantly (rand-int 100)) [:a :b :c])
user=> (43 43 43){% endraw %}
{% endhighlight %}


