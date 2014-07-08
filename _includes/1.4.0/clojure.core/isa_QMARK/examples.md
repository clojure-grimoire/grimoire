### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (import 'java.util.PriorityQueue)
java.util.PriorityQueue

user=> (bases PriorityQueue)
(java.util.AbstractQueue java.io.Serializable)

user=> (import 'java.util.AbstractQueue)
java.util.AbstractQueue

user=> (isa? PriorityQueue AbstractQueue)
true

user=> (bases AbstractQueue)
(java.util.AbstractCollection java.util.Queue)

user=> (isa? PriorityQueue java.util.AbstractCollection)
true

user=> (isa? PriorityQueue java.util.Queue)
true

user=> (isa? java.util.PriorityQueue java.util.TreeMap)
false{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (derive ::Feline ::Animal)
nil
user=> (derive ::Cat ::Feline)
nil

user=> (derive ::Lion ::Feline)
nil

user=> (isa? ::Lion ::Feline)
true

user=> (isa? ::Lion ::Animal)
true

user=> (isa? ::Tuna ::Feline)
false{% endraw %}
{% endhighlight %}


