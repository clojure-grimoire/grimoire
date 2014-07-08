### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (decimal? 1)
false

user=> (decimal? 1.0)
false

user=> (decimal? 1M)
true

user=> (decimal? 99999999999999999999999999999999999)
false

user=> (decimal? 1.0M)
true{% endraw %}
{% endhighlight %}


