### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user> (or true false false)
true

user> (or true true true)
true

user> (or false false false)
false

user> (or nil nil)
nil

user> (or false nil)
nil

user> (or true nil)
true

;; or doesn't evaluate if the first value is true
user> (or true (println "foo"))
true

;; order matters
user> (or (println "foo") true)
foo
true{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


