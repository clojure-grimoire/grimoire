### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (coll? {})
true

user=> (coll? #{})
true

user=> (coll? [])
true

user=> (coll? ())
true

user=> (coll? 4)
false

user=> (coll? "fred")
false

user=> (coll? true)
false

user=> (coll? nil)
false
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (coll? {:a 10 :b 20}) ; map is a collection of map-entries
true{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; contrast to example code for sequential?
;;
user> (coll? '(1 2 3))
true
user> (coll? [1 2 3])
true
user> (coll? (range 1 5))
true
user> (coll? 1)
false
user> (coll? {:a 2 :b 1})
true
user> (coll? {:a 2 :b 1})  ; in contrast to sequential?, coll? returns true for a hash map
true
user> (sequential? {:a 2 :b 1})
false{% endraw %}
{% endhighlight %}


