### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (keep-indexed #(if (odd? %1) %2) [:a :b :c :d :e])
(:b :d){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (keep-indexed #(if (pos? %2) %1) [-9 0 29 -7 45 3 -8])
(2 4 5)
;; f takes 2 args: 'index' and 'value' where index is 0-based
;; when f returns nil the index is not included in final result
user=> (keep-indexed (fn [idx v]
                       (if (pos? v) idx)) [-9 0 29 -7 45 3 -8])
(2 4 5){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(defn position [x coll & {:keys [from-end all] :or {from-end false all false}}]
  (cond
   (true? from-end) (last (keep-indexed #(if (= x %2) %1) coll))
   (true? all) (keep-indexed #(if (= x %2) %1) coll)
   :else (first (keep-indexed #(if (= x %2) %1) coll))))

user> (position [1 1] [[1 0][1 1][2 3][1 1]])
1
user> (position [1 1] [[1 0][1 1][2 3][1 1]] :from-end true)
3
user> (position [1 1] [[1 0][1 1][2 3][1 1]] :all true)
(1 3)

user> (def foo (shuffle (range 10)))
#'user/foo
user> foo
(5 8 9 1 2 7 0 6 3 4)
user> (position 5 foo)
0
user> (position 0 foo)
6{% endraw %}
{% endhighlight %}


