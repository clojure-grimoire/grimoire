### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user> (defonce foo 5)
#'user/foo

user> foo
5

;; defonce does nothing the second time
user> (defonce foo 10)
nil

user> foo
5{% endraw %}
{% endhighlight %}


