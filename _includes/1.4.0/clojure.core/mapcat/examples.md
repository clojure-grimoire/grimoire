### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (mapcat reverse [[3 2 1 0] [6 5 4] [9 8 7]])
(0 1 2 3 4 5 6 7 8 9)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (mapcat (fn [[k v]]
                 (for [[k2 v2] v]
                   (concat [k k2] v2)))
         '{:a {:x (1 2) :y (3 4)}
           :b {:x (1 2) :z (5 6)}})

((:a :x 1 2) (:a :y 3 4) (:b :x 1 2) (:b :z 5 6)){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
user=> (require '[clojure.string :as cs])
nil

;; Suppose you have a fn in a `map` that itself returns
;; multiple values.
user=> (map #(cs/split % #"\d") ["aa1bb" "cc2dd" "ee3ff"])
(["aa" "bb"] ["cc" "dd"] ["ee" "ff"])

;; Now, if you want to concat them all together, you *could*
;; do this:
user=> (apply concat (map #(cs/split % #"\d") ["aa1bb" "cc2dd" "ee3ff"]))
("aa" "bb" "cc" "dd" "ee" "ff")

;; But `mapcat` can save you a step:
user=> (mapcat #(cs/split % #"\d") ["aa1bb" "cc2dd" "ee3ff"])
("aa" "bb" "cc" "dd" "ee" "ff")
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
;; Suppose you've got a function that takes a value
;; and returns a list of things from it, for example:
(defn f1
  [n]
  [(- n 1) n (+ n 1)])

(f1 1)
;=> [0 1 2]

;; Perhaps you'd like to map it onto each item in a collection:
(map f1 [1 2 3])
;=> ([0 1 2] [1 2 3] [2 3 4])

;; But suppose you wanted them all concatenated? You could do this:
(apply concat (map f1 [1 2 3]))
;=> (0 1 2 1 2 3 2 3 4)

;; Or you could get the same thing with `mapcat`:
(mapcat f1 [1 2 3])
;=> (0 1 2 1 2 3 2 3 4)
{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
; Flatten a map, consing keys on to each nested vector
(mapcat (fn [[k vs]] (map (partial cons k) vs)) {:foo [[1 2] [3 2]] :bar [[3 1]]})
;=> ((:foo 1 2) (:foo 3 2) (:bar 3 1))
{% endraw %}
{% endhighlight %}


