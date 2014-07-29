### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (defprotocol Area (get-area [this]))
Area

user=> (defrecord Rectangle [width height]
                  Area
                  (get-area [this]
                    (* width height)))
user.Rectangle

(extends? Area Rectangle)
true
{% endraw %}
{% endhighlight %}


