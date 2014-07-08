### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (bigint 30)
30


;; Actually do something BigInteger-ish... (http://download.oracle.com/javase/6/docs/api/)

user=> (def x (bigint 97))
#'user/x

user=> (.isProbablePrime (.toBigInteger x) 100)
true
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user> (= (bigint 42) (clojure.lang.BigInt/fromBigInteger (BigInteger. "42"))
true
user> (= 42N (bigint 42))
true
user> (= 42 (bigint 42))
true
user> (= 42 (clojure.lang.BigInt/fromBigInteger (BigInteger. "42"))
true
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
user> (reduce * (repeat 20 1000))
ArithmeticException integer overflow  clojure.lang.Numbers.throwIntOverflow (Numbers.java:1388)

user> (reduce * (repeat 20 (bigint 1000)))
1000000000000000000000000000000000000000000000000000000000000N
{% endraw %}
{% endhighlight %}


