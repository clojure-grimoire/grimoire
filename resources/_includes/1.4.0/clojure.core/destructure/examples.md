### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
; look ma, no let!
(map #(intern *ns* (first %) (eval (last %))) (partition 2 (destructure '[[a b] ["a" "b"]])))
user=> a
"a"
user=> b
"b"{% endraw %}
{% endhighlight %}


