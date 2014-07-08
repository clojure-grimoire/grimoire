### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user> (clojure.string/blank? nil)
true

user> (clojure.string/blank? false)
true

user> (clojure.string/blank? "   ")
true

user> (clojure.string/blank? " a ")
false{% endraw %}
{% endhighlight %}


