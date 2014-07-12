### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (difference #{1 2 3})
#{1 2 3}
user=> (difference #{1 2} #{2 3})
#{1}
user=> (difference #{1 2 3} #{1} #{1 4} #{3})
#{2}{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (difference (set (keys {:1 1 :2 2 :3 3})) (set (keys {:1 1 :2 2})))
#{:3}
user=> (difference (set (keys {:1 1 :2 2})) (set (keys {:1 1 :2 2 :3 3})))
#{}{% endraw %}
{% endhighlight %}


