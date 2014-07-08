### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (let [x `(2 3)]
         `(1 ~x))
(1 (2 3))

user=> (let [x `(2 3)]
         `(1 ~@x))
(1 2 3)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> `(1 2 ~(list 3 4))

(1 2 (3 4))

user=> `(1 2 ~@(list 3 4))

(1 2 3 4)

; borrowed from StackOverflow:
; http://stackoverflow.com/questions/4571042/can-someone-explain-clojures-unquote-splice-in-simple-terms{% endraw %}
{% endhighlight %}


