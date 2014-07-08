### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; true:
(== 1)
(== 1 1)
(== 1/1, 2/2, 3/3, 4/4)
(== 1, 1.0, 1/1)
(== :foo)


;; false:
(== 1 2)
(== 1 \1)
(== 1 "1"){% endraw %}
{% endhighlight %}


