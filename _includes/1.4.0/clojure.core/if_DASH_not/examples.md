### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (defn has-neg [coll]
  (if-not (empty? coll)   ;;  = (if (not (empty? coll)) ...
    (or (neg? (first coll)) (recur (rest coll)))))
#'user/has-neg

user=> (has-neg [])
nil

user=> (has-neg [1 2 -3 4])
true{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (if-not (zero? 0) :then :else)
:else{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


