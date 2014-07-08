### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Note: Documentation should be "starting from the first item for which
;; (pred item) returns logical false, i.e. either of the values false or nil.

user=> (drop-while neg? [-1 -2 -6 -7 1 2 3 4 -5 -6 0 1])
(1 2 3 4 -5 -6 0 1)
{% endraw %}
{% endhighlight %}


