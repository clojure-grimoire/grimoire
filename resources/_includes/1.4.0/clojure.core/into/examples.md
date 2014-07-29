### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
; Maps can be constructed from a sequence of 2-vectors or a sequence
; of maps
user=> (into (sorted-map) [ [:a 1] [:c 3] [:b 2] ] )
{:a 1, :b 2, :c 3}
user=> (into (sorted-map) [ {:a 1} {:c 3} {:b 2} ] )
{:a 1, :b 2, :c 3}

; When maps are the input source, they convert into an unordered sequence
; of key-value pairs, encoded as 2-vectors
user=> (into [] {1 2, 3 4})
[[1 2] [3 4]]
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
; Items are conj'ed one at a time, which puts them at the head of
; the destination list
user=> (into () '(1 2 3))
(3 2 1)
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(defn test-key-inclusion-cols
  "return all values in column1 that arent' in column2"
  [column1 column2]
  (filter (complement (into #{} column2)) column1))
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
; Change from one type of map to another
user=> (into (sorted-map) {:b 2 :c 3 :a 1})
{:a 1, :b 2, :c 3}{% endraw %}
{% endhighlight %}


