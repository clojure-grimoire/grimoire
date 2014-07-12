### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (and true true)
true

user=> (and true false)
false

user=> (and false false)
false

user=> (and '() '())
()

user=> (and '[] '[])
[]

user=> (and 0 1)  ; Note that this is *not* bitwise 'and'
1

user=> (and 1 0)
0

user=> (and (constantly true) (> 2 1))
true{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


