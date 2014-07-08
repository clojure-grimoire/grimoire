### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; In this example you can see that the first time the delay is forced
;; the println is executed however the second dereference shows just the
;; precomputed value.

user=> (def my-delay (delay (println "did some work") 100))
#'user/my-delay

user=> @my-delay
did some work
100

user=> @my-delay
100
{% endraw %}
{% endhighlight %}


