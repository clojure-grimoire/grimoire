### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (meta (vary-meta 'foo assoc :a 1))
{:a 1}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; continuing from the previous with-meta example
user=> (def wm (with-meta [1 2 3] {:my "meta"}))
#'user/wm

user=> wm
[1 2 3]

user=> (meta wm)
{:my "meta"}

user=> (def new-wm (vary-meta wm assoc :your "new meta"))
#'user/new-wm

user=> new-wm
[1 2 3]

user=> (meta new-wm)
{:my "meta", :your "new meta"}

{% endraw %}
{% endhighlight %}


