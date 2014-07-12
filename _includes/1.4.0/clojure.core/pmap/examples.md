### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; This function operates just like map.  See
;; clojure.core/map for more details.
user=> (pmap inc [1 2 3 4 5])
(2 3 4 5 6){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; create a function that simulates a long running process using Thread/sleep
(defn long-running-job [n]
    (Thread/sleep 3000) ; wait for 3 seconds
    (+ n 10))

;; used `doall` to eagerly evaluate `map`, which evaluates lazily by default

;; notice that the total elapse time is almost 3 secs * 4
user=> (time (doall (map long-running-job (range 4))))
"Elapsed time: 11999.235098 msecs"
(10 11 12 13)

;; notice that the total elapse time is almost 3 secs only
user=> (time (doall (pmap long-running-job (range 4))))
"Elapsed time: 3200.001117 msecs"
(10 11 12 13){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; pmap is implemented using Clojure futures.  See examples for 'future'
;; for discussion of an undesirable 1-minute wait that can occur before
;; your standalone Clojure program exits if you do not use shutdown-agents.{% endraw %}
{% endhighlight %}


