### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; adding a mouse pressed callback to a Swing component:

(defn add-mousepressed-listener
  [component f & args]
  (let [listener (proxy [MouseAdapter] []
                     (mousePressed [event]
                                   (apply f event args)))]
    (.addMouseListener component listener)
    listener))
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; BUG: proxy dispatches *only* on name, not arity:
user=> (let [p (proxy [java.io.InputStream] [] (read [] -1))]
         (println (.read p))
         (println (.read p (byte-array 3) 0 3)))

-1
ArityException Wrong number of args (4) passed to: core$eval213$fn  clojure.lang.AFn.throwArity (AFn.java:437)
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; You can, however, provide multiple-arity functions to get some support
;; for overloading
user> (let [p (proxy [java.io.InputStream] []
          (read ([] 1)
            ([^bytes bytes] 2)
            ([^bytes bytes off len] 3)))]
  (println (.read p))
  (println (.read p (byte-array 3)))
  (println (.read p (byte-array 3) 0 3)))

1
2
3
nil{% endraw %}
{% endhighlight %}


