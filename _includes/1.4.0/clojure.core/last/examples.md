### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
<pre>user=> (last [1 2 3 4 5])
5
user=> (last ["a" "b" "c" "d" "e"])
"e"
user=> (last {:one 1 :two 2 :three 3})
[:three 3]
user=> (last [])
nil
</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
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


