### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; iterate Ad Infinitum starting at 5 using the inc (increment) function
user=> (iterate inc 5)
(5 6 7 8 9 10 11 12 13 14 15 ... n

;; limit results
user=> (take 5 (iterate inc 5))
(5 6 7 8 9)

user=> (take 10 (iterate (partial + 2) 0))
(0 2 4 6 8 10 12 14 16 18)

user=> (take 20 (iterate (partial + 2) 0))
(0 2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38)

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (def powers-of-two (iterate (partial * 2) 1))
#'user/powers-of-two

user=> (nth powers-of-two 10)
1024
user=> (take 10 powers-of-two)
(1 2 4 8 16 32 64 128 256 512)
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; demonstrating the power of iterate
;; to generate the Fibonacci sequence
;; uses +' to promote to BigInt
user=> (def fib (map first (iterate (fn [[a b]] [b (+' a b)]) [0 1])))
#'user/fib

user=> (take 10 fib)
(0 1 1 2 3 5 8 13 21 34){% endraw %}
{% endhighlight %}


