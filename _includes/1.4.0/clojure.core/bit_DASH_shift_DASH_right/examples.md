### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Convert number into bits:
user=> (defn bits [n s]
         (take s
               (map
                 (fn [i] (bit-and 0x01 i))
                 (iterate
                   (fn [i] (bit-shift-right i 1))
                   n))))
#'user/bits

user=> (map (fn [n] (bits n 3)) (range 8))
((0 0 0) (1 0 0) (0 1 0) (1 1 0) (0 0 1) (1 0 1) (0 1 1) (1 1 1))
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (bit-shift-right 2r1101 0)
13
user=> (bit-shift-right 2r1101 1)
6
user=> (bit-shift-right 2r1101 2)
3{% endraw %}
{% endhighlight %}


