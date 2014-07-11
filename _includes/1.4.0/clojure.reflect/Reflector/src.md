{% highlight clojure linenos %}
(defprotocol Reflector
  "Protocol for reflection implementers."
  (do-reflect [reflector typeref]))
{% endhighlight %}
