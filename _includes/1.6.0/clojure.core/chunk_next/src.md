## source
{% highlight clojure linenos %}
(defn ^:static ^clojure.lang.ISeq chunk-next ^clojure.lang.ISeq [^clojure.lang.IChunkedSeq s]
  (.chunkedNext s))
{% endhighlight %}
