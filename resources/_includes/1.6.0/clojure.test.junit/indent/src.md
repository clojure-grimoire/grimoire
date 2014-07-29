{% highlight clojure %}
(defn indent
  []
  (dotimes [n (* *depth* 4)] (print " ")))
{% endhighlight %}
