### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (clojure.walk/prewalk-replace '{a b} '(c (d a)))
(c (d b)){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
(use 'clojure.walk)

(prewalk-replace {:a 1 :b 2} [:a :b])
;=> [1 2]

(prewalk-replace {:a 1 :b 2} [:a :b :c])
;=> [1 2 :c]

(prewalk-replace {:a 1 :b 2} [:a :b [:a :b] :c])
;=> [1 2 [1 2] :c]{% endraw %}
{% endhighlight %}


