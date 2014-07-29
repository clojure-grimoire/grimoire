### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user> (seq? 1)
false
user> (seq? [1])
false
user> (seq? (seq [1]))
true{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; contrast to example code for sequential?
;;
user> (seq? '(1 2 3))
true
user> (seq? [1 2 3])   ; for sequential?, returns true
false
user> (seq? (range 1 5))
true
user> (seq? 1)
false
user> (seq? {:a 2 :b 1})
false
user> {% endraw %}
{% endhighlight %}


