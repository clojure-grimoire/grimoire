## source
{% highlight clojure linenos %}
(defn suite-attrs
  [package classname]
  (let [attrs {:name classname}]
    (if package
      (assoc attrs :package package)
      attrs)))
{% endhighlight %}
