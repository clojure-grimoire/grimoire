{% highlight clojure %}
(defprotocol Reflector
  "Protocol for reflection implementers."
  (do-reflect [reflector typeref]))
{% endhighlight %}
