### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (partition-by #(= 3 %) [1 2 3 4 5])
((1 2) (3) (4 5)){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (partition-by odd? [1 1 1 2 2 3 3])
((1 1 1) (2 2) (3 3))

user=> (partition-by even? [1 1 1 2 2 3 3])
((1 1 1) (2 2) (3 3))
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; (this is part of a solution from 4clojure.com/problem 30)
user=> (partition-by identity "Leeeeeerrroyyy")
((\L) (\e \e \e \e \e \e) (\r \r \r) (\o) (\y \y \y)){% endraw %}
{% endhighlight %}


