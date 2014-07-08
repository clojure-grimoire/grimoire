### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (defprotocol P (id [this]))
P
user=> (extend-protocol P
         String
         (id [this] this)
         clojure.lang.Symbol
         (id [this] (name this))
         clojure.lang.Keyword
         (id [this] (name this)))
nil
user=> (extenders P)
(java.lang.String clojure.lang.Symbol clojure.lang.Keyword)
{% endraw %}
{% endhighlight %}


