### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (disj #{1 2 3}) ; disjoin nothing
#{1 2 3}

user=> (disj #{1 2 3} 2) ; disjoin 2
#{1 3}

user=> (disj #{1 2 3} 4) ; disjoin not existed item
#{1 2 3}

user=> (disj #{1 2 3} 1 3) ; disjoin several items at once
#{2}{% endraw %}
{% endhighlight %}


