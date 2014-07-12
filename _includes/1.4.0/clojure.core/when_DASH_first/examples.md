### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (when-first [a [1 2 3]] a)
1
user=> (when-first [a []] a)
nil
user=> (when-first [a nil] a)
nil{% endraw %}
{% endhighlight %}


