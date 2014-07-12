### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def *foo* "(println [1 2 3])")
#'user/*foo*

user=> *foo*
"(println [1 2 3])"

user=> (eval *foo*)   ; Notice eval'ing a string does not work.
"(println [1 2 3])"

user=> (eval (read-string *foo*))
[1 2 3]
nil{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (eval '(let [a 10] (+ 3 4 a)))
17
{% endraw %}
{% endhighlight %}


