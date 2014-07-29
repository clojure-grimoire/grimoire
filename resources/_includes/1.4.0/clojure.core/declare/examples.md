### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (defn foo []
         (undefined-func))
; Evaluation aborted. Unable to resolve symbol: undefined-func in this context
nil

user=> (declare undefined-func)
#'user/undefined-func

user=> (defn foo []
         (undefined-func))
#'user/foo
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (declare show)
#'user/show
user=> (defn welcome [user-name] (prn (show) user-name))
#'user/welcome
user=> (defn show [] (prn "welcome "))
#'user/show
user=> (welcome "lu4nx")
"welcome "
nil "lu4nx"
nil
user=> {% endraw %}
{% endhighlight %}


