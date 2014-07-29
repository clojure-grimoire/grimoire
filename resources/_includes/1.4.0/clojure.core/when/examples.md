### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (when (= 1 1) true)
true

user=> (when (not= 1 1) true)
nil{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (def has-value (when true
                            (println "Hello World")
                            "Returned Value"))
Hello World
#'user/has-value

user=> has-value
"Returned Value"

{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


