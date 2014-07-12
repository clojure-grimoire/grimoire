### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (= 1)
true
user=> (= 1 1)
true
user=> (= 1 2)
false
user=> (= 1 1 1)
true
user=> (= 1 1 2)
false
user=> (= '(1 2) [1 2])
true
user=> (= nil nil)
true

;; It should be noted that equality is not defined for Java arrays.
;; Instead you can convert them into sequences and compare them that way.
;; (= (seq array1) (seq array2))
{% endraw %}
{% endhighlight %}


