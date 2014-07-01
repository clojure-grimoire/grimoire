## source
{% highlight clojure linenos %}
(defmacro comment
  "Ignores body, yields nil"
  {:added "1.0"}
  [& body])
{% endhighlight %}
