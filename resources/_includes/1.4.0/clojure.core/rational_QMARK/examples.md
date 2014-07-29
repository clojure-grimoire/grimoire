### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (rational? 1)
true
user=> (rational? 1.0)
false
user=> (class 1.0)
java.lang.Double

;; Note that decimal? only returns true if n is a BigDecimal.{% endraw %}
{% endhighlight %}


