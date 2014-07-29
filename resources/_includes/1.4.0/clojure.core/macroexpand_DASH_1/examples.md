### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (macroexpand-1 '(defstruct mystruct[a b]))
(def mystruct (clojure.core/create-struct [a b]))
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (macroexpand-1 '(-> c (+ 3) (* 2)))
(clojure.core/-> (clojure.core/-> c (+ 3)) (* 2)){% endraw %}
{% endhighlight %}


