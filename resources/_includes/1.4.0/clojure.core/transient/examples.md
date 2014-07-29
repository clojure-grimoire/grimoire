### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; As seen on http://clojure.org/transients
;; array is initially made transient, modified then
;; finally made persistent.

(defn vrange2 [n]
  (loop [i 0 v (transient [])]
    (if (< i n)
      (recur (inc i) (conj! v i))
      (persistent! v))))

user=> (vrange2 10)
[0 1 2 3 4 5 6 7 8 9]{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user> (def foo (transient [1 2 3]))
#'user/foo
user> (get foo 0)
1
user> (count foo)
3
user> (nth foo 0)
1
user> (def bar (transient {:honda 7 :kagawa 23 :ienaga 14}))
#'user/bar
user> (get bar :kagawa)
23
user> (count bar)
3

;; There is a known bug in Clojure 1.3 where contains? always returns false for
;; transients.  http://dev.clojure.org/jira/browse/CLJ-700
;; contains? works fine for persistent data structures.
user> (contains? bar :kagawa)  ; Caution!
false
user> (def bar2 (persistent! bar))
#'user/bar2
user> (contains? bar2 :kagawa) ; Caution!
true
{% endraw %}
{% endhighlight %}


