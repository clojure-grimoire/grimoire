### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (defstruct person :name :age :height)
#'user/person

user=> (struct person "george" 22 115)
{:name "george", :age 22, :height 115}{% endraw %}
{% endhighlight %}


