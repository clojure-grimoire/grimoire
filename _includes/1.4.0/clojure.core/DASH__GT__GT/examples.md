### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; An example of using the "thread-last" macro to get
;; the sum of the first 10 even squares.
user=> (->> (range)
            (map #(* % %))
            (filter even?)
            (take 10)
            (reduce +))
1140

;; This expands to:
user=> (reduce +
               (take 10
                     (filter even?
                             (map #(* % %)
                                  (range)))))
1140
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (def c 5)
user=> (->> c (+ 3) (/ 2) (- 1))
3/4

;; and if you are curious why
user=> (use 'clojure.walk)
user=> (macroexpand-all '(->> c (+ 3) (/ 2) (- 1)))
(- 1 (/ 2 (+ 3 c)))

{% endraw %}
{% endhighlight %}


