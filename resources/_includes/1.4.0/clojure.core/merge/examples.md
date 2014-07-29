### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (merge {:a 1 :b 2 :c 3} {:b 9 :d 4})
{:d 4, :a 1, :b 9, :c 3}{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (merge {:a 1} nil)
{:a 1}

user=> (merge nil {:a 1})
{:a 1}

user> (merge nil nil)
nil
{% endraw %}
{% endhighlight %}


