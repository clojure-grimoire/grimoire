### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (doseq [x [1 2 3]
               y [1 2 3]]
         (prn (* x y)))
1
2
3
2
4
6
3
6
9
nil
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (doseq [[x y] (map list [1 2 3] [1 2 3])]
         (prn (* x y)))
1
4
9
nil

;; where
user=> (map list [1 2 3] [1 2 3])
((1 1) (2 2) (3 3)){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (doseq [[[a b] [c d]] (map list {:1 1 :2 2} {:3 3 :4 4})]
         (prn (* b d)))
3
8
nil

;; where
user=> (map list {:1 1 :2 2} {:3 3 :4 4})
(([:1 1] [:3 3]) ([:2 2] [:4 4])){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
user=> (doseq [[k v] (map identity {:1 1 :2 2 :3 3})]
         (prn k v))
:1 1
:2 2
:3 3
nil

;; where
user=> (map identity {:1 1 :2 2 :3 3})
([:1 1] [:2 2] [:3 3])

;; or simply
user=> (doseq [[k v] {:1 1 :2 2 :3 3}]
         (prn k v))
:1 1
:3 3
:2 2
nil{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
; Multiple sequences results in a Cartesian cross of their values.
user=> (doseq [a [1 2]
               b [3 4]]
         (println a b))
1 3
1 4
2 3
2 4
nil{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
; Keywords :let, :when, and :while are supported, the same as "for"
user=> (doseq [x (range 6)
               :when (odd? x)
               :let [y (* x x)] ]
         (println [x y]) )
[1 1]
[3 9]
[5 25]
nil
user=> (doseq [x (range 99)
               :let [y (* x x)]
               :while (< y 30)
              ]
         (println [x y]) )
[0 0]
[1 1]
[2 4]
[3 9]
[4 16]
[5 25]
nil
user=>
{% endraw %}
{% endhighlight %}


