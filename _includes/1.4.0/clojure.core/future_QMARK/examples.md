### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def f (future (inc 0)))
#'user/f

user=> (future? f)
true

user=> (future? 1)
false
{% endraw %}
{% endhighlight %}


