### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
<pre>user=> (empty? ())
true
user=> (empty? '(1))
false</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (every? empty? ["" [] () '() {} #{} nil])
true

;example of recommended idiom for testing if not empty
user=> (every? seq ["1" [1] '(1) {:1 1} #{1}])
true{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
<pre>
user=> (drop-while empty? ["" [] "foobar"])
("foobar")
</pre>{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
user=> (empty? nil)
true{% endraw %}
{% endhighlight %}


