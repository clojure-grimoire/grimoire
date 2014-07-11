{% highlight clojure linenos %}
(def ^:dynamic *open-url-script* (when (macosx?) "/usr/bin/open"))
{% endhighlight %}
