## source
{% highlight clojure linenos %}
(defn ^:static  ^clojure.lang.IChunk chunk-first ^clojure.lang.IChunk [^clojure.lang.IChunkedSeq s]
  (.chunkedFirst s))
{% endhighlight %}
