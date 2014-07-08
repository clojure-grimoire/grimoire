### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
<pre>user=&gt; (nnext '(1 2 3))
(3)
</pre>

<pre>user=&gt; (nnext [])
nil 
</pre>

<pre>user=&gt; (nnext ['(a b c) '(b a c) '(c b a) '(a c b)])
((c b a) (a c b)) 
</pre>

<pre>user=&gt; (nnext {:a 1, :b 2, :c 3, :d 4})
([:c 3] [:d 4]) 
</pre>

<pre>user=&gt; (nnext #{:a :b :c})
(:c)
</pre>
{% endraw %}
{% endhighlight %}


