### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (map-indexed (fn [idx itm] [idx itm]) "foobar")
([0 \f] [1 \o] [2 \o] [3 \b] [4 \a] [5 \r])

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; or simply
user=> (map-indexed vector "foobar")
([0 \f] [1 \o] [2 \o] [3 \b] [4 \a] [5 \r]){% endraw %}
{% endhighlight %}


