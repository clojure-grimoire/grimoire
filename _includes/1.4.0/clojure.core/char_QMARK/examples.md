### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (char? \a)
true

user=> (char? 22)
false

user=> (char? "a")
false

user=> (char? (first "abc"))
true{% endraw %}
{% endhighlight %}


