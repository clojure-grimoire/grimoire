### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; derive let you build a hierarchy but parents/ancestors/descendants and isa? let you query the hierarchy
(derive ::rect ::shape)
(derive ::square ::rect)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (derive ::Cat ::Feline)
nil

user=> (derive ::Lion ::Feline)
nil

user=> (isa? ::Lion ::Feline)
true

user=> (isa? ::Tuna ::Feline)
false{% endraw %}
{% endhighlight %}


