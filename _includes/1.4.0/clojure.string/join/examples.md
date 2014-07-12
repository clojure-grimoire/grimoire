### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
<pre>
user=> (clojure.string/join ", " [1 2 3])
"1, 2, 3"
</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Splits a string on space character and joins
;; the resulting collection with a line feed character

(use '[clojure.string :only (join split)])

user=> (println
         (join "\n"
           (split "The Quick Brown Fox" #"\s")))
The
Quick
Brown
Fox
nil{% endraw %}
{% endhighlight %}


