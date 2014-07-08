### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Calculate the sum of all numbers under 1000:
user=> (reduce + (take-while (partial > 1000) (iterate inc 0)))
499500{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (take-while neg? [-2 -1 0 1 2 3])
(-2 -1)

user=> (take-while neg? [-2 -1 0 -1 -2 3])
(-2 -1)

user=> (take-while neg? [ 0 1 2 3])
()

user=> (take-while neg? [])
()

user=> (take-while neg? nil)
(){% endraw %}
{% endhighlight %}


