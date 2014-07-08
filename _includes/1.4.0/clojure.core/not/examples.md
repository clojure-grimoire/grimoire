### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (not true)
false
user=> (not false)
true
;; acts as complement of `boolean`
user=> (boolean "a string")
true
user=> (not "a string")
false
user=> (boolean 1)
true
user=> (not 1)
false{% endraw %}
{% endhighlight %}


