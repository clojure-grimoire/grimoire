{% highlight clojure linenos %}
(defmulti ^:dynamic tap-report (fn [data] (:type data)))
{% endhighlight %}
