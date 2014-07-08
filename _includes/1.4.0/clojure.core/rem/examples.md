### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (rem 10 9)
1
user=> (rem 2 2)
0{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; rem and mod are commonly used to get the remainder.
;; mod means Gaussian mod, so the result is always
;; non-negative.  Don't confuse it with ANSI C's %
;; operator, which despite being although pronounced
;; 'mod' actually implements rem, i.e. -10 % 3 = -1.

user=> (mod -10 3)
2

user=> (rem -10 3)
-1{% endraw %}
{% endhighlight %}


