### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
<pre>user=&gt; (select-keys {:a 1 :b 2} [:a])
{:a 1}
user=&gt; (select-keys {:a 1 :b 2} [:a :c])
{:a 1}
</pre>{% endraw %}
{% endhighlight %}


