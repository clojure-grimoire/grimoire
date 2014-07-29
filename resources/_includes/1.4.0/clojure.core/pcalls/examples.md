### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (pcalls function-1 function-2 ...)

(result1 result2 ...){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; pcalls is implemented using Clojure futures.  See examples for 'future'
;; for discussion of an undesirable 1-minute wait that can occur before
;; your standalone Clojure program exits if you do not use shutdown-agents.{% endraw %}
{% endhighlight %}


