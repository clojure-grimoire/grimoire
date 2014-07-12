### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (partition 4 [0 1 2 3 4 5 6 7 8 9])
((0 1 2 3) (4 5 6 7))

user=> (partition-all 4 [0 1 2 3 4 5 6 7 8 9])
((0 1 2 3) (4 5 6 7) (8 9))
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (partition-all 2 4 [0 1 2 3 4 5 6 7 8 9])
((0 1) (4 5) (8 9)){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
Caution: Partitioning lazy sequence code freeze

(def l [1 2 3 4 5])
;create a simple lazy sequence function testing only
;(rdr l) returns a lazy sequence from l
(def rdr (fn reader[x] (cons (first x) (lazy-seq (reader  (rest x))))))

;the line below will freeze
(doall (partition-all 2 (rdr l)) )

;add-in a take-while statement do exit the lazy sequence on nil
(doall (partition-all 2 (take-while (complement nil?) (rdr l)))){% endraw %}
{% endhighlight %}


