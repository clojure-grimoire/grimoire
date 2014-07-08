### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (use 'clojure.pprint)
nil

;; By default, columns are in the order returned by (keys (first rows))
user=> (print-table [{:a 1 :b 2 :c 3} {:b 5 :a 7 :c "dog"}])
=============
:a | :c  | :b
=============
1  | 3   | 2
7  | dog | 5
=============
nil

;; If there are keys not in the first row, and/or you want to specify only
;; some, or in a particular order, give the desired keys as the first arg.
user=> (print-table [:b :a] [{:a 1 :b 2 :c 3} {:b 5 :a 7 :c "dog"}])
=======
:b | :a
=======
2  | 1
5  | 7
=======
nil
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (use 'clojure.pprint 'clojure.reflect)
nil
user=> (def x (:members (reflect clojure.lang.BigInt)))
#'user/x
user=> (print-table [:name :type :flags] (sort-by :name x))
======================================================================
:name               | :type                | :flags
======================================================================
ONE                 | clojure.lang.BigInt  | #{:static :public :final}
ZERO                | clojure.lang.BigInt  | #{:static :public :final}
add                 |                      | #{:public}
bipart              | java.math.BigInteger | #{:public :final}
bitLength           |                      | #{:public}
byteValue           |                      | #{:public}
clojure.lang.BigInt |                      | #{:private}
doubleValue         |                      | #{:public}
equals              |                      | #{:public}
floatValue          |                      | #{:public}
fromBigInteger      |                      | #{:static :public}
fromLong            |                      | #{:static :public}
hashCode            |                      | #{:public}
intValue            |                      | #{:public}
longValue           |                      | #{:public}
lpart               | long                 | #{:public :final}
lt                  |                      | #{:public}
multiply            |                      | #{:public}
quotient            |                      | #{:public}
remainder           |                      | #{:public}
shortValue          |                      | #{:public}
toBigInteger        |                      | #{:public}
toString            |                      | #{:public}
valueOf             |                      | #{:static :public}
======================================================================
nil
{% endraw %}
{% endhighlight %}


