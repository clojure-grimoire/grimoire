### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (counted? [:a :b :c])
true

user=> (counted? '(:a :b :c))
true

user=> (counted? {:a 1 :b 2 :c 3})
true

user=> (counted? #{:a :b :c})
true

user=> (counted? "asdf")
false

user=> (counted? (into-array Integer/TYPE [1 2 3]))
false{% endraw %}
{% endhighlight %}


