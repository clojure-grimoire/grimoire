### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (apropos "temp")
()

user=> (require 'clojure.template)
nil

user=> (apropos "temp")
(apply-template do-template)
{% endraw %}
{% endhighlight %}


