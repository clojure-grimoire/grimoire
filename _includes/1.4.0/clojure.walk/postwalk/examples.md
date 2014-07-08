### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(use 'clojure.walk)
(let [counter (atom -1)]
   (postwalk (fn [x]
                [(swap! counter inc) x])
              {:a 1 :b 2}))

=> [6 {2 [[0 :a] [1 1]], 5 [[3 :b] [4 2]]}] {% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;;example of removing namespaces from all keys in a nested data structure
(def thing {:page/tags [{:tag/category "lslsls"}]})
(postwalk #(if(keyword? %)(keyword (name %)) %) thing)
{:tags [{:category "lslsls"}]}{% endraw %}
{% endhighlight %}


