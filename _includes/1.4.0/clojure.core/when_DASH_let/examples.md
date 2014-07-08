### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Very useful when working with sequences. Capturing the return value
;; of `seq` brings a performance gain in subsequent `first`/`rest`/`next`
;; calls. Also the block is guarded by `nil` punning.

(defn drop-one
  [coll]
  (when-let [s (seq coll)]
    (rest s)))

user=> (drop-one [1 2 3])
(2 3)
user=> (drop-one [])
nil
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


