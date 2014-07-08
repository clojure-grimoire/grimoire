### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (fn? 5)
false
user=> (fn? inc)
true
user=> (fn? (fn []))
true
user=> (fn? #(5))
true{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; Even though maps, sets, vectors and keywords behave as functions:
user=> ({:a 1} :a)
1

;; fn? still returns false for them because they are not created using fn:
user=> (fn? {:a 1})
false
{% endraw %}
{% endhighlight %}


