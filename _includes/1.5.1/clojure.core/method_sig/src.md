## source
{% highlight clojure linenos %}
(defn method-sig [^java.lang.reflect.Method meth]
  [(. meth (getName)) (seq (. meth (getParameterTypes))) (. meth getReturnType)])
{% endhighlight %}
