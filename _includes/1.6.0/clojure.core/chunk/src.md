## source
{% highlight clojure linenos %}
(defn ^:static ^clojure.lang.IChunk chunk [^clojure.lang.ChunkBuffer b]
  (.chunk b))
{% endhighlight %}
