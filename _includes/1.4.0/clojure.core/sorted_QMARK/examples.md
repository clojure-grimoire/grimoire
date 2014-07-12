### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (sorted? (sorted-set 5 3 1 2 4))
true
user=> (sorted? (sorted-map :a 1 :c 3 :b 2))
true

;; Note you can't just pass in a collection that happens to be sorted.
user=> (sorted? [1 2 3 4 5])
false
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
=> (sorted? (sort [1 2]))
false{% endraw %}
{% endhighlight %}


