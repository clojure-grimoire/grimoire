### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (clojure.string/upper-case "MiXeD cAsE")
"MIXED CASE"{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; Non-character symbols will be returned back
user=> (clojure.string/upper-case ",.!@#$%^&*()")
",.!@#$%^&*()"{% endraw %}
{% endhighlight %}


