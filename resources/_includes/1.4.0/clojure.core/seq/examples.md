### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (seq '())
nil

user=> (seq '(1))
(1)

user=> (seq "")
nil

user=> (seq "abc")
(\a \b \c)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;(seq x) is the recommended idiom for testing if a collection is not empty
user=> (every? seq ["1" [1] '(1) {:1 1} #{1}])
true{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
; seq can be used to turn a map into a list of vectors
; notice how the list is built adding elements to the start of the list
; not to the end, like in vectors
user=> (seq {:key1 "value1" :key2 "value2"})
([:key2 "value 2"] [:key1 "value 1"]){% endraw %}
{% endhighlight %}


