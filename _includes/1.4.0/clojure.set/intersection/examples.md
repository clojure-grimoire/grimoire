### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (intersection #{1})
#{1}
user=> (intersection #{1 2} #{2 3})
#{2}
user=> (intersection #{1 2} #{2 3} #{3 4})
#{}
user=> (intersection #{1 :a} #{:a 3} #{:a})
#{:a}
{% endraw %}
{% endhighlight %}


