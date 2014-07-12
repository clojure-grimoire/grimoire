### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (get [1 2 3] 1)
2
user=> (get [1 2 3] 5)
nil
user=> (get {:a 1 :b 2} :b)
2
user=> (get {:a 1 :b 2} :z "missing")
"missing"

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; to get an index of the element of a vector, use .indexOf
user=> (def v ["one" "two" "three" "two"])
#'user/v

user=> (.indexOf v "two")
1

user=> (.indexOf v "foo")
-1
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (get (System/getenv) "SHELL")
"/bin/bash"

user=> (get (System/getenv) "PATH")
"/usr/local/bin:/sbin:/usr/sbin:/usr/bin:/bin"{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; 'get' is not the only option
user=> (def my-map {:a 1 :b 2 :c 3})

;; maps act like functions
user=> (my-map :a)
1

;; even keys act like functions
user=> (:b my-map)
2{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
user=> (get '(a b c) 1)
nil

;; use nth
user=> (nth '(a b c) 1)
b{% endraw %}
{% endhighlight %}


