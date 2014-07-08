### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
<pre>
user=> (dorun 5 (repeatedly #(println "hi")))
hi
hi
hi
hi
hi
hi
nil
</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (let [x (atom 0)]
         (dorun (take 10 (repeatedly #(swap! x inc))))
         @x)
10{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
user=> (dorun (map #(println "hi" %) ["mum" "dad" "sister"]))
hi mum
hi dad
hi sister
nil{% endraw %}
{% endhighlight %}


