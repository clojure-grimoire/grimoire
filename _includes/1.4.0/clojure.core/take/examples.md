### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (take 3 '(1 2 3 4 5 6))
(1 2 3)

user=> (take 3 [1 2 3 4 5 6])
(1 2 3)

user=> (take 3 [1 2])
(1 2)

user=> (take 1 [])
(){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (take 3 (drop 5 (range 1 11)))
(6 7 8){% endraw %}
{% endhighlight %}


