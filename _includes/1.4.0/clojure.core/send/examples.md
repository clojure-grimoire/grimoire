### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def my-agent (agent 100))
#'user/my-agent
user=> @my-agent
100

;; Note the following happens asynchronously in a thread
;; pool
user=> (send my-agent + 100)
#<Agent@5afc0f5: 200>

;; Assuming the addition has completed the value will
;; now be updated when we look at it.
user=> @my-agent
200{% endraw %}
{% endhighlight %}


