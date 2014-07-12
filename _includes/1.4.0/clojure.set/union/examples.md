### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (union)
#{}
user=> (union #{1 2})
#{1 2}
user=> (union #{1 2} #{2 3})
#{1 2 3}
user=> (union #{1 2} #{2 3} #{3 4})
#{1 2 3 4}
{% endraw %}
{% endhighlight %}


