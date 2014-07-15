{% include 1.5.0/clojure.core/as_DASH__GT/examples.md %}

### Example 0
[permalink](#example-0)
{% highlight clojure %}
{% raw %}
user=> (as-> 1 a (inc a) (inc a) (inc a))
4
{% endraw %}
{% endhighlight %}

### Example 1
[permalink](#example-1)
{% highlight clojure %}
{% raw %}
; Using the simple threading macro ->
(-> shape
  (rotate 30)
  (scale 1.2)
  (translate 10 -2))

; Using as->
(as-> shape s
  (rotate s 30)
  (scale s 1.2)
  (translate s 10 -2))
{% endraw %}
{% endhighlight %}

[Please add examples!](https://github.com/arrdem/grimoire/edit/master/_includes/1.6.0/clojure.core/as_DASH__GT/examples.md)
