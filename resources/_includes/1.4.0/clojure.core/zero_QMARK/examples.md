### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (zero? 0)
true
user=> (zero? 0.0)
true
user=> (zero? 1)
false
user=> (zero? 0x0)
true
user=> (zero? 3.14159265358M)
false
user=> (zero? (/ 1 2))
false{% endraw %}
{% endhighlight %}


