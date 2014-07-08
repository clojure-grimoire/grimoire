### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
<pre>
user=&gt; (nth ["a" "b" "c" "d"] 0)             
"a"
user=&gt; (nth ["a" "b" "c" "d"] 1)             
"b"
user=&gt; (nth [] 0 "nothing found")
"nothing found"
user=&gt; (nth [0 1 2] 77 1337)
1337
</pre>{% endraw %}
{% endhighlight %}


