### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (take 5 (cycle ["a" "b"]))
("a" "b" "a" "b" "a")

user=> (take 10 (cycle (range 0 3)))
(0 1 2 0 1 2 0 1 2 0)

{% endraw %}
{% endhighlight %}


