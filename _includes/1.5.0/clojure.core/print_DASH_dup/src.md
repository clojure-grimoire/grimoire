{% highlight clojure linenos %}
(defmulti print-dup (fn [x writer] (class x)))
{% endhighlight %}
