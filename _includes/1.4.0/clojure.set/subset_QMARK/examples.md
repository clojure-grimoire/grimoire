### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user> (clojure.set/subset? #{2 3} #{1 2 3 4})
true

user> (clojure.set/subset? #{2 4} #{1 2 3 4})
true

user> (clojure.set/subset? #{2 5} #{1 2 3 4})
false{% endraw %}
{% endhighlight %}


