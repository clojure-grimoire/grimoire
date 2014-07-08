### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;these two functions are equivalent

user=> (take 5 (repeatedly #(rand-int 11)))
(6 6 3 9 8)

user=> (repeatedly 5 #(rand-int 11))
(1 8 6 9 6)

;compare with repeat
user=> (repeat 5 (rand-int 100))
(94 94 94 94 94){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(defn counter []
  (let [tick (atom 0)]
    #(swap! tick inc)))

(def tick (counter))

user=> (take 10 (repeatedly tick))
(1 2 3 4 5 6 7 8 9 10){% endraw %}
{% endhighlight %}


