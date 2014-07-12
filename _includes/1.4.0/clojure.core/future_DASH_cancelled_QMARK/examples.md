### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def f (future (inc 0)))
#'user/f

user=> (future-cancel f)
false

user=> (future-cancelled? f)
false

user=> (future-done? f)
true

user=> @f
1
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (def f (future (Thread/sleep 5000) (inc 0)))
#'user/f

user=> (future-cancel f)
true

user=> (future-cancelled? f)
true

user=> (future-done? f)
true

user=> @f
java.util.concurrent.CancellationException (NO_SOURCE_FILE:0){% endraw %}
{% endhighlight %}


