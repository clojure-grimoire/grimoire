### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; (quot m n) is the value of m/n, rounded towards 0 to the nearest integer.
;; m, n need not be integers.

user=> (quot 10 3)
3

user=> (quot 11 3)
3

user=> (quot 12 3)
4

user=> (quot -5.9 3)
-1.0

user=> (quot 10 -3)
-3

user=> (quot 15 0)
ArithmeticException / by zero  clojure.lang.Numbers.quotient (Numbers.java:1764)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; note that the "/" function and the quot function are not equivalent

user=> (= (/ 4 2) (quot 4 2))
true

user=> (= (/ 3 2) (quot 3 2))
false
{% endraw %}
{% endhighlight %}


