### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def a (atom 0))
#'user/a
user=> (compare-and-set! a 10 20)
false
user=> @a
0
user=> (compare-and-set! a 0 10)
true
user=> @a
10
{% endraw %}
{% endhighlight %}


