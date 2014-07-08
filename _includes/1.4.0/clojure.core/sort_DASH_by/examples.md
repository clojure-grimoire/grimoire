### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (sort-by count ["aaa" "bb" "c"])
("c" "bb" "aaa")

user=> (sort-by first [[1 2] [2 2] [2 3]])
([1 2] [2 2] [2 3])

<pre>user=> (sort-by first > [[1 2] [2 2] [2 3]])
([2 2] [2 3] [1 2]){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (sort-by :rank [{:rank 2} {:rank 3} {:rank 1}])
({:rank 1} {:rank 2} {:rank 3}){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
(def x [{:foo 2 :bar 11}
        {:bar 99 :foo 1}
        {:bar 55 :foo 2}
        {:foo 1 :bar 77}])

;sort by :foo, and where :foo is equal, sort by :bar?
(sort-by (juxt :foo :bar) x)
;=>({:foo 1, :bar 77} {:bar 99, :foo 1} {:foo 2, :bar 11} {:bar 55, :foo 2}){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
(def x [{:foo 2 :bar 11}
 {:bar 99 :foo 1}
 {:bar 55 :foo 2}
 {:foo 1 :bar 77}])
; sort-by given key order (:bar)
(def order [55 77 99 11])
(sort-by
  #((into {} (map-indexed (fn [i e] [e i]) order)) (:bar %))
  x)
;=> ({:bar 55, :foo 2} {:foo 1, :bar 77} {:bar 99, :foo 1} {:foo 2, :bar 11}){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
;sort entries in a map by value
user=> (sort-by val > {:foo 7, :bar 3, :baz 5})
([:foo 7] [:baz 5] [:bar 3]){% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure linenos %}
{% raw %}
;; Warning: You can sort a Java array and get back a sorted immutable Clojure
;; data structure, but it will also change the input Java array, by sorting it.
;; Copy the array before sorting if you want to avoid this.

user=> (def x (to-array [32 -5 4 11]))
#'user/x

user=> (seq x)
(32 -5 4 11)

user=> (def y (sort-by - x))
#'user/y

;; Return sorted sequence
user=> y
(32 11 4 -5)

;; but also modifies x, because it used the array to do the sorting.
user=> (seq x)
(32 11 4 -5)

;; One way to avoid this is copying the array before sorting:
user=> (def y (sort-by - (aclone x)))
#'user/y{% endraw %}
{% endhighlight %}


