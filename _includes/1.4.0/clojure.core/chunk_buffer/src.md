## source
{% highlight clojure linenos %}
(defn ^:static ^clojure.lang.ChunkBuffer chunk-buffer ^clojure.lang.ChunkBuffer [capacity]
  (clojure.lang.ChunkBuffer. capacity))
{% endhighlight %}
