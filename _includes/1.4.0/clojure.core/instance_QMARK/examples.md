### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (instance? Long 1)
true
user=> (instance? Integer 1)
false
user=> (instance? Number 1)
true
user=> (instance? String 1)
false
user=> (instance? String "1")
true
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (def al (new java.util.ArrayList))
#'user/al
user=> (instance? java.util.Collection al)
true
user=> (instance? java.util.RandomAccess al)
true
user=> (instance? java.lang.String al)
false{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; Some things are more than what they seem to be at first glance
user=> (instance? clojure.lang.IFn +)
true
user=> (instance? clojure.lang.Keyword :a)
true
user=> (instance? clojure.lang.IFn :a)
true
user=> (instance? clojure.lang.IFn {:a 1})
true
{% endraw %}
{% endhighlight %}


