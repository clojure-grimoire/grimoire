### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (distinct [1 2 1 3 1 4 1 5])
(1 2 3 4 5){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (def fractions
         (for [n (range 1 100) d (range (inc n) 100)]
           (let [gcd (clojure.contrib.math/gcd n d)]
             (/ (/ n gcd) (/ d gcd)))))
;; all irreducible fractions with denominator < 100
;; (1/2 1/3 ... 1/99 2/3 1/2 2/5 1/3 ...)

user=> (count fractions)
4851

user=> (count (distinct fractions))
3003
{% endraw %}
{% endhighlight %}


