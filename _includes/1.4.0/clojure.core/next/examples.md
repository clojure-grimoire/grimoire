### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (next '(:alpha :bravo :charlie))
(:bravo :charlie)

user=> (next (next '(:one :two :three)))
(:three)

user=> (next (next (next '(:one :two :three))))
nil{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; next is used in the recursive call.  (This is a naive implementation for illustration only.  Using `rest` is usually preferred over `next`.)

(defn my-map [func a-list]
  (when a-list
    (cons (func (first a-list))
          (my-map func (next a-list))))){% endraw %}
{% endhighlight %}


