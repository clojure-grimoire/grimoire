### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Everything except `false' and `nil' is true in boolean context.
user=> (into {} (map #(vector % (boolean %)) [true false nil [] {} '() #{} ""]))
{true true, false false, nil false, [] true, {} true, #{} true, "" true}

user=> (clojure.pprint/pp)
{true true,
 false false,
 nil false,
 [] true,
 {} true,
 #{} true,
 "" true}
nil
{% endraw %}
{% endhighlight %}


