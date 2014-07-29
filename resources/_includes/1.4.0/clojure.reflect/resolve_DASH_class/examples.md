### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;;Check if class c exists on the classpath
(use '[clojure.reflect :only [resolve-class]])

(defn class-exists? [c]
  (resolve-class (.getContextClassLoader (Thread/currentThread)) c))

user=> (class-exists? 'org.joda.time.DateTime)
nil{% endraw %}
{% endhighlight %}


