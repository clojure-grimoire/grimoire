### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
<pre>user=&gt; (max 1 2 3 4 5)  
5
user=&gt; (max 5 4 3 2 1)
5
user=&gt; (max 100)
100
</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; If elements are already in a sequence, use apply
user=> (apply max [1 2 3 4 3])
4
user=> (apply max '(4 3 5 6 2))
6{% endraw %}
{% endhighlight %}


