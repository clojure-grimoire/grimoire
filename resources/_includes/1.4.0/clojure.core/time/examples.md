### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user> (time (Thread/sleep 100))
"Elapsed time: 100.284772 msecs"
nil{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;when working with lazy seqs
(time (doall (...))){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Time how long it takes to write a string to a file 100 times
(defn time-test []
  (with-open [w (writer "test.txt" :append false)]
    (dotimes [_ 100]
      (.write w "I am being written to a file."))))


user=> (time (time-test))
"Elapsed time: 19.596371 msecs"{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
user=> (time (Thread/sleep 1000))
"Elapsed time: 1000.267483 msecs"
nil
user=> (with-out-str (time (Thread/sleep 1000)))
"\"Elapsed time: 1010.12942 msecs\"\n"

{% endraw %}
{% endhighlight %}


