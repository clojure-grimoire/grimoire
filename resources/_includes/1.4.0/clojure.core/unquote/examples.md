### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (let [x 2]
         `(1 x 3))
(1 user/x 3)

user=> (let [x 2]
         `(1 ~x 3))
(1 2 3)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> `(1 (dec 3) 3)

(1 (clojure.core/dec 3) 3)

user => `(1 ~(dec 3) 3)

(1 2 3){% endraw %}
{% endhighlight %}


