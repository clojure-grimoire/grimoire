### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def x (promise))
#'user/x
;; Trying to deref at this point will make your repl wait forever


user=> (deliver x 100)
#&lt;core$promise$reify__5534@4369a50b: 100&gt;

;; the promise has been delivered, deref x will return immediately
user=> @x
100
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; Create a promise
user> (def p (promise))
#'user/p ; p is our promise

;; Check if was delivered/realized
user> (realized? p)
false ; No yet

;; Delivering the promise
user> (deliver p 42)
#<core$promise$reify__5727@47122d: 42>

;; Check again if it was delivered
user> (realized? p)
true ; Yes!

;; Deref to see what has been delivered
user> @p
42

;; Note that @ is shorthand for deref
user> (deref p)
42{% endraw %}
{% endhighlight %}


