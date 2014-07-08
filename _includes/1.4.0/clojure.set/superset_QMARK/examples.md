### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(use '[clojure.set :only [superset?]])

user=> (superset? #{0} #{0})
true

user=> (superset? #{0 1} #{0})
true

user=> (superset? #{0} #{0 1})
false
{% endraw %}
{% endhighlight %}


