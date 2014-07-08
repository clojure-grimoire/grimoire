### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (min 1 2 3 4 5)
1
user=> (min 5 4 3 2 1)
1
user=> (min 100)
100{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; If elements are already in a sequence, use apply
user=> (apply min [1 2 3 4 3])
1
user=> (apply min '(4 3 5 6 2))
2{% endraw %}
{% endhighlight %}


