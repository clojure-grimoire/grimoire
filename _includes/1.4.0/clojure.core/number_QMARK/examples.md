### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (number? 1)
true
user=> (number? 1.0)
true
user=> (number? :a)
false
user=> (number? nil)
false
user=> (number? "23")
false{% endraw %}
{% endhighlight %}


