### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}

user=> (def f (future (Thread/sleep 5000) (inc 0)))
#'user/f

user=> (future-cancel f)
true

user=> (future-cancelled? f)
true{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}

user=> (def f (future (inc 0)))
#'user/f

user=> (future-cancel f)
false

user=> (future-cancelled? f)
false{% endraw %}
{% endhighlight %}


