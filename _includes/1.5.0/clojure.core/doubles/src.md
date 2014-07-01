## source
{% highlight clojure linenos %}
(definline doubles
  "Casts to double[]"
  {:added "1.0"}
  [xs] `(. clojure.lang.Numbers doubles ~xs))
{% endhighlight %}
