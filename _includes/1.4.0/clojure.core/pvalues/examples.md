### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; expressions are calculated in parallel

user=> (pvalues (expensive-calc-1) (expensive-calc-2))
(2330 122)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; pvaules is implemented using Clojure futures.  See examples for 'future'
;; for discussion of an undesirable 1-minute wait that can occur before
;; your standalone Clojure program exits if you do not use shutdown-agents.{% endraw %}
{% endhighlight %}


