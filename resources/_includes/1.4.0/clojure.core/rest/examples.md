### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
<pre>user=&gt; (rest [1 2 3 4 5])
(2 3 4 5)
user=&gt; (rest ["a" "b" "c" "d" "e"])
("b" "c" "d" "e")</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (rest '())
(){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (rest nil)
(){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; A simple implementation using rest for recursing over a collection.  Note that (seq coll) is used as the test.
(defn my-map [func coll]
     (when-let [s (seq coll)]
        (cons (func (first s))
	      (my-map func (rest s))))){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
user=>(rest '(1 2 3 4 5))
'(2 3 4 5){% endraw %}
{% endhighlight %}


