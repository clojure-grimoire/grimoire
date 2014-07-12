### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (map val {:a 1 :b 2})
(1 2)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
(val (first {:one :two}))
:two{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;syntactic sugar for (val)
(:doc (meta #'meta)){% endraw %}
{% endhighlight %}


