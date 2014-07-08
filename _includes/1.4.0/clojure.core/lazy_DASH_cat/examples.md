### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (lazy-cat [1 2 3] [4 5 6])
(1 2 3 4 5 6)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; N.B. this example holds onto the head of a lazy seq which should generally be avoided
(def fib-seq
     (lazy-cat [0 1] (map + (rest fib-seq) fib-seq)))

(take 10 fib-seq){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; When the producer function produces a collection, not an element,
;; lazy-cat is usable.
user=> (defn n-repeat [n] (lazy-cat (repeat n n) (n-repeat (inc n))))
#'user/n-repeat

user=> (take 6 (n-repeat 1))
(1 2 2 3 3 3)

user=> (take 12 (n-repeat 1))
(1 2 2 3 3 3 4 4 4 4 5 5)
{% endraw %}
{% endhighlight %}


