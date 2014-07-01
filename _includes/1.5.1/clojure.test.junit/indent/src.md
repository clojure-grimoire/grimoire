## source
{% highlight clojure linenos %}
(defn indent
  []
  (dotimes [n (* *depth* 4)] (print " ")))
{% endhighlight %}
