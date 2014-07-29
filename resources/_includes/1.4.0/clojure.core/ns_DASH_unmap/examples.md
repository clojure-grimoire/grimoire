### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def foo 1)
#'user/foo

user=> foo
1

user=> (ns-unmap 'user 'foo) ; explicit
nil

user=> (ns-unmap *ns* 'foo) ; convenient
nil

user=> foo
"Unable to resolve symbol: foo in this context"
{% endraw %}
{% endhighlight %}


