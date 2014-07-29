### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user> (defn foo [] (println "foo"))
#'user/foo

user> (def bar "bar")
#'user/bar

user> (clojure.test/function? foo)
true

user> (clojure.test/function? bar)
false{% endraw %}
{% endhighlight %}


