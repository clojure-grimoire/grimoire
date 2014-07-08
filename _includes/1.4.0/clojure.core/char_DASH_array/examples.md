### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user> (char-array "asdf")
#<char[] [C@167fc18>

user> (seq (char-array "asdf"))
(\a \s \d \f)

user> (seq (char-array 10))
(\^@ \^@ \^@ \^@ \^@ \^@ \^@ \^@ \^@ \^@){% endraw %}
{% endhighlight %}


