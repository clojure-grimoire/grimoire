### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def foo (ref {}))
#'user/foo

user=> (dosync
         (ref-set foo {:foo "bar"}))
{:foo "bar"}

user=> @foo
{:foo "bar"}
{% endraw %}
{% endhighlight %}


