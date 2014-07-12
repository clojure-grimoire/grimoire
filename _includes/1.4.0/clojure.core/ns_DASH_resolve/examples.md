### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (defn f [n] (* n n n))
#'user/f
user=> ((ns-resolve *ns* (symbol "f")) 10)
1000{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; See also http://clojure.org/namespaces for information on namespaces in Clojure and how to inspect and manipulate them{% endraw %}
{% endhighlight %}


