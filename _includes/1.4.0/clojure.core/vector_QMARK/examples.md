### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (vector? [1 2 3])
true
user=> (vector? '(1 2 3))
false
user=> (vector? (vec '(1 2 3)))
true{% endraw %}
{% endhighlight %}


