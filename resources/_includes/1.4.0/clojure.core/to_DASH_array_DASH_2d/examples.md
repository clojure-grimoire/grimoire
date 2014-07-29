### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user> (def a (to-array-2d [[1 2 3][4 5 6]]))
#'user/a
user> (alength a)
2
user> (alength (aget a 0))
3
user> (aget a 0 0)
1
user> (aget a 0 1)
2
user> (aget a 0 2)
3
user> (aget a 1 0)
4
user> (aget a 2 0)
â†’ ERROR
nil

user> {% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; quick example of a ragged array where the length of each element of the
;; 2d array is unique

user=> (def a (to-array-2d [[0][1 2][3 4 5][6 7 8 9]]))
#'user/a
user=> (map alength [(aget a 0)(aget a 1)(aget a 2)])
(1 2 3)
user=>{% endraw %}
{% endhighlight %}


