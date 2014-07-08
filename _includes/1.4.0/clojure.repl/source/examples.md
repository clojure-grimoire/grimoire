### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (source max)
;; prints in console:
(defn max
  "Returns the greatest of the nums."
  {:added "1.0"}
  ([x] x)
  ([x y] (if (> x y) x y))
  ([x y & more]
   (reduce max (max x y) more)))
{% endraw %}
{% endhighlight %}


