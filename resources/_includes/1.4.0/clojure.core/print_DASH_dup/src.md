{% highlight clojure %}
(defmulti print-dup (fn [x writer] (class x)))
{% endhighlight %}
