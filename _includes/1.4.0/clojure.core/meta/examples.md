### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (meta #'first)
{:ns #&lt;Namespace clojure.core&gt;, :name first, :file "clojure/core.clj", :line 39, :arglists ([coll]), :doc "Returns the first item in the collection. Calls seq on its\n    argument. If coll is nil, returns nil."}
{% endraw %}
{% endhighlight %}


