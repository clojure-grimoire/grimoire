## source
{% highlight clojure linenos %}
(defn start-case
  [name classname]
  (start-element 'testcase true {:name name :classname classname}))
{% endhighlight %}
