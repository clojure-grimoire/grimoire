### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def large-vec (vec (range 0 10000)))
#'user/large-vec

user=> (time (last large-vec))
"Elapsed time: 1.279841 msecs"
9999

user=> (time (peek large-vec))
"Elapsed time: 0.049238 msecs"
9999
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (peek '(:a :b :c))
:a{% endraw %}
{% endhighlight %}


