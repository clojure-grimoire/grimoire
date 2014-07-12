### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Nothing is printed because map returns a lazy-seq
user=> (def foo (map println [1 2 3]))
#'user/foo

;; doall forces the seq to be realized
user=> (def foo (doall (map println [1 2 3])))
1
2
3
#'user/foo

;; where
(doall (map println [1 2 3]))
1
2
3
(nil nil nil){% endraw %}
{% endhighlight %}


