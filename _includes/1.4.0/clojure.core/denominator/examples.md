### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; denominator always returns the denominator of the reduced fraction
;;
user=> (denominator (/ 2 3))
3
user=> (denominator (/ 3 6))
2
user=> (map denominator [(/ 3 2) (/ 2 3) (/ 4 5) (/ 4 6)])
(2 3 5 3)
user=>{% endraw %}
{% endhighlight %}


