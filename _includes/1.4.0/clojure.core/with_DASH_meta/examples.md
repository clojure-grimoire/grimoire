### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (with-meta [1 2 3] {:my "meta"})
[1 2 3]

user=> (meta (with-meta [1 2 3] {:my "meta"}))
{:my "meta"}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; the same example above in a simplified way
user=> (def wm (with-meta [1 2 3] {:my "meta"}))
#'user/wm

user=> wm
[1 2 3]

user=> (meta wm)
{:my "meta"}{% endraw %}
{% endhighlight %}


