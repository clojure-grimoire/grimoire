### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (every? even? '(2 4 6))
true
user=> (every? even? '(1 2 3))
false{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; you can use every? with a set as the predicate to return true if
;; every member of a collection is in the set
user=> (every? #{1 2} [1 2 3])
false
user=> (every? #{1 2} [1 2])
true

;; or use a hash-map as the predicate with every? to return true
;; if every member of a collection is a key within the map
user=> (every? {1 "one" 2 "two"} [1 2])
true
user=> (every? {1 "one" 2 "two"} [1 2 3])
false{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; this is kind of weird IMO... but it works that way (the same for vectors)
user=> (every? true? '())
true
user=> (every? false? '())
true{% endraw %}
{% endhighlight %}


