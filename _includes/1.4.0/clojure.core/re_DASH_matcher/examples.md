### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (def *matcher* (re-matcher #"\d+" "abc12345def"))
#'user/*matcher*

user=> (re-find *matcher*)
"12345"{% endraw %}
{% endhighlight %}


