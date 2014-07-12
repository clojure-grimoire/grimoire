### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (second '(:alpha :bravo :charlie))
:bravo

user=> (second [1 2 3])
2

user=> (second {:a 1 :b 2 :c 3})
[:b 2]

user=> (second #{1 2 3})
2
{% endraw %}
{% endhighlight %}


