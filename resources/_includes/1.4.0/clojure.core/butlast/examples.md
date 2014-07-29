### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (butlast [1 2 3])
(1 2)
user=> (butlast (butlast [1 2 3]))
(1)
user=> (butlast (butlast (butlast [1 2 3])))
nil{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;really slow reverse
;put the last item of the list at the start of a new list, and recur over all but the last item of the list.
;butlast acts similar to next in that it returns null for a 1-item list.

(defn my-reverse
  ([a-list]
     (cond (= a-list nil) nil
           :else (cons (last a-list)
                       (my-reverse (butlast a-list)))))){% endraw %}
{% endhighlight %}


