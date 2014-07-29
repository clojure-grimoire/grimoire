### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Create a Java ArrayList using the 0 argument constructor
user=> (def a  (new java.util.ArrayList))
#'user/a
user=> (.add a "aaa")
true
user=> (.add a "bbb")
true
user=> a
#<ArrayList [aaa, bbb]>
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Create another ArrayList and add integers using the doto macro
user=> (def ai (doto (new java.util.ArrayList) (.add 1) (.add 2) (.add 0)))
#'user/ai
user=> ai
#<ArrayList [1, 2, 0]>{% endraw %}
{% endhighlight %}


