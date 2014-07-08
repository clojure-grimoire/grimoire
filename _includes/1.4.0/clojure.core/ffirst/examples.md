### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (ffirst '([]))
nil

user=> (ffirst ['(a b c) '(b a c)])
a

user=> (ffirst '([a b c] [b a c]))
a{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (first {:b 2 :a 1 :c 3})
[:b 2]

user=> (ffirst {:b 2 :a 1 :c 3})
:b{% endraw %}
{% endhighlight %}


