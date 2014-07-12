### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (not= 1 1)
false

user=> (not= 1 2)
true

user=> (not= true true)
false

user=> (not= true false)
true

user=> (not= true true true true)
false

user=> (not= true true false true)
true
{% endraw %}
{% endhighlight %}


