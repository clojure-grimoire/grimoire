`reduced` allows you to short-circuit a call to `reduce`, ending the reduction early.

{% raw %}
### Example 1
[permalink](#example-1)
{% highlight clojure %}
(reduce (fn [a v] (if-not (a v) (conj a v) (reduced a))) 
         #{} 
         infinite-lazy-sequence)
 => #{1 2 3 4}
{% endhighlight %}
{% endraw %}

[Example sourced from StackOverflow](http://stackoverflow.com/a/15625740/173062)
