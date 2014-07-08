### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (to-array [1 2 3])
#&lt;Object[] [Ljava.lang.Object;@3a7e479a>
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(def hello (to-array "Hello World!"))

(aget hello 1)
\e

(aset hello 1 \b) ;;Mutability! Watch out!
\b

(dotimes [n (alength hello)] (print (aget hello n)))
Hbllo World!{% endraw %}
{% endhighlight %}


