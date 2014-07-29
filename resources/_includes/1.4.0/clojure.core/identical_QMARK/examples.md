### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def x 1)
#'user/x
user=> (identical? x x)
true
user=> (identical? x 1)
true
user=> (identical? x 2)
false
user=> (identical? x ((constantly 1) 8))
true
user=> (identical? 'a 'a)
false{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (def x {:foo 1, :bar -3})
#'user/x
user=> (def y {:foo 1, :bar -3})
#'user/y
;; Values are equal, but different objects were constructed
user=> (= x y)
true
user=> (identical? x y)
false
{% endraw %}
{% endhighlight %}


