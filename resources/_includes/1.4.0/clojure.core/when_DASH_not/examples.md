### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (map
         #(when-not (= %2 %3) [%1 %2 %3])
         (iterate inc 0)
         [:a :b :c]
         [:a :a :a])

(nil [1 :b :a] [2 :c :a])
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


