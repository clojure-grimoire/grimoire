### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
<pre>user=&gt; (def x (atom 10))
#'user/x

user=&gt; @x
10

user=&gt; (reset! x 20)
20

user=&gt; @x
20
</pre>{% endraw %}
{% endhighlight %}


