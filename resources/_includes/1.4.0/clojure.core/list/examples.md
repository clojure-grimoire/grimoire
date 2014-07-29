### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (list 'a 'b 'c 'd 'e 'f 'g)
(a b c d e f g)
user=> (list 1 2 3)
(1 2 3){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (let [m {:1 1 :2 2 :3 3 :4 4}] (map list (keys m) (vals m)))
((:1 1) (:2 2) (:3 3) (:4 4)){% endraw %}
{% endhighlight %}


