### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (array-map [1 2] [3 4 5])
{[1 2] [3 4 5]}{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (array-map :a 10)
{:a 10}

user=> (array-map :a 10 :b 20)
{:a 10 :b 20}

user=> (apply array-map [:a 10 :b 20 :c 30])
{:a 10 :b 20 :c 30}

user=> (apply assoc {} [:a 10 :b 20 :c 30]) ;same result using assoc
{:a 10 :b 20 :c 30}
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (keys (assoc (array-map :foo 10 :bar 20) :baz 30))
(:baz :foo :bar)
; baz is first; :foo and :bar follow the order given to array-map{% endraw %}
{% endhighlight %}


