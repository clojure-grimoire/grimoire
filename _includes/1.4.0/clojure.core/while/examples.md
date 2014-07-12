### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def a (atom 10))
#'user/a

user=> (while (pos? @a) (do (println @a) (swap! a dec)))
10
9
8
7
6
5
4
3
2
1
nil{% endraw %}
{% endhighlight %}


