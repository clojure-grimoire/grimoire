### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (partition 4 (range 20))
((0 1 2 3) (4 5 6 7) (8 9 10 11) (12 13 14 15) (16 17 18 19))

user=> (partition 4 (range 22))
((0 1 2 3) (4 5 6 7) (8 9 10 11) (12 13 14 15) (16 17 18 19))

user=> (partition 4 6 (range 20))
((0 1 2 3) (6 7 8 9) (12 13 14 15))

user=> (partition 4 6 ["a"] (range 20))
((0 1 2 3) (6 7 8 9) (12 13 14 15) (18 19 "a"))

user=> (partition 4 6 ["a" "b" "c" "d"] (range 20))
((0 1 2 3) (6 7 8 9) (12 13 14 15) (18 19 "a" "b"))
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (partition 3 1 [:a :b :c :d :e :f])
((:a :b :c) (:b :c :d) (:c :d :e) (:d :e :f))
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; When there are less than n items in the coll, partition's behaviour
;; depends on whether there is a pad or not

;; without pad
user> (partition 10 [1 2 3 4])
()
;; again, without pad
user> (partition 10 10 [1 2 3 4])
()
;; with a pad this time (note: the pad is an empty sequence)
user> (partition 10 10 nil [1 2 3 4])
((1 2 3 4))
;; or, explicit empty sequence instead of nil
user> (partition 10 10 [] [1 2 3 4])
((1 2 3 4))
{% endraw %}
{% endhighlight %}


