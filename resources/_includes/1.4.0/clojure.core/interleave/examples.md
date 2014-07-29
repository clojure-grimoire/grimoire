### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; This example takes a list of keys and a separate list of values and
;; inserts them into a map.
user=> (apply assoc {}
         (interleave [:fruit :color :temp]
                     ["grape" "red" "hot"]))

{:temp "hot", :color "red", :fruit "grape"}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Simple example:
user=> (interleave [:a :b :c] [1 2 3])
(:a 1 :b 2 :c 3){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; The shortest input stops interleave:

user=> (interleave [:a :b] (iterate inc 1))
(:a 1 :b 2){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
(def s1 [[:000-00-0000 "TYPE 1" "JACKSON" "FRED"]
         [:000-00-0001 "TYPE 2" "SIMPSON" "HOMER"]
         [:000-00-0002 "TYPE 4" "SMITH" "SUSAN"]])

(interleave (map #(nth % 0 nil) s1) (map #(nth % 1 nil) s1)){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
(def s1 [[:000-00-0000 "TYPE 1" "JACKSON" "FRED"]
         [:000-00-0001 "TYPE 2" "SIMPSON" "HOMER"]
         [:000-00-0002 "TYPE 4" "SMITH" "SUSAN"]])

(def cols [0 2 3])

(defn f1
  [s1 col]
  (map #(get-in s1 [% col] nil) (range (count s1))))

(apply interleave (map (partial f1 s1) cols))
(:000-00-0000 "JACKSON" "FRED" :000-00-0001 "SIMPSON" "HOMER" :000-00-0002 "SMITH" "SUSAN"){% endraw %}
{% endhighlight %}


