## source
{% highlight clojure linenos %}
(defn print-simple [o, ^Writer w]
  (print-meta o w)
  (.write w (str o)))
{% endhighlight %}
