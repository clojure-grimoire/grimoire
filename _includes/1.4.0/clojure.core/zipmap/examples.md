### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (zipmap [:a :b :c :d :e] [1 2 3 4 5])
{:e 5, :d 4, :c 3, :b 2, :a 1}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; 4 is not included in the result
user=> (zipmap [:a :b :c] [1 2 3 4])
{:c 3, :b 2, :a 1}

{% endraw %}
{% endhighlight %}


