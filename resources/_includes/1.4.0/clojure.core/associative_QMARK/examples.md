### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (associative? [1 2 3]) ; vector
true
user=> (associative? '(1 2 3)) ; list
false
user=> (associative? {:a 1 :b 2}) ; map
true
user=> (associative? #{:a :b :c}) ; set
false
user=> (associative? "fred") ; string
false
{% endraw %}
{% endhighlight %}


