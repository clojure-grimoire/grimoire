`reduced` allows you to short-circuit a call to `reduce`, ending the reduction early.

{% raw %}
### Example 1
[permalink](#example-1)
{% highlight clojure %}
(reduce
  (fn [acc v]
    (if (< v 5)
      (conj acc v)
      (reduced acc)))
  []
  (range))
 => [0 1 2 3 4]
{% endhighlight %}
{% endraw %}
