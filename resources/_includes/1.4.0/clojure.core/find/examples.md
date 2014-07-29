### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
<pre>user=&gt; (find {:b 2 :a 1 :c 3} :d)
nil 

user=&gt; (find {:b 2 :a 1 :c 3} :a)
[:a 1] 
</pre>
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (find [:a :b :c :d] 2)
[2 :c]

user=> (find [:a :b :c :d] 5)
nil{% endraw %}
{% endhighlight %}


