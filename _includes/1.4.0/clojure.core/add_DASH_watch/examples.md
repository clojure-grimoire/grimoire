### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Add useful context to watcher function:
(defn watch-agent [_agent context]
    (let [watch-fn (fn [_context _key _ref old-value new-value] ;...
               )]
        (add-watch _agent nil (partial watch-fn context))))
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; timing of agent actions and watches using nrepl (ymmv)
user> (def a (agent 0))
#'user/a
user> a
#<Agent@2bd9c3e7: 0>
user> (add-watch a :key (fn [k r os ns] (print k r os ns)))
#<Agent@2bd9c3e7: 0>
user> (send a inc)
:key #<Agent@2bd9c3e7: 1> 0 1
#<Agent@2bd9c3e7: 1>
user> a
:key #<Agent@2bd9c3e7: 2> 1 2
#<Agent@2bd9c3e7: 2>
{% endraw %}
{% endhighlight %}


