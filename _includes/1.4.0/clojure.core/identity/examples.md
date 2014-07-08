### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (identity 4)
4{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (filter identity [1 2 3 nil 4 false true 1234])
(1 2 3 4 true 1234){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
user=> (map #(%1 %2) (cycle [inc identity]) [1 2 3 4 5 6 7 8 9 10])
(2 2 4 4 6 6 8 8 10 10)
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
user=> (partition-by identity (sort "abcdaabccc"))
((\a \a \a) (\b \b) (\c \c \c \c) (\d))
{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
user=> (map first (partition-by identity [1 1 2 3 3 1 1 5 5]))
(1 2 3 1 5){% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure linenos %}
{% raw %}
user=> (group-by identity "abracadabra")
{\a [\a \a \a \a \a], \b [\b \b], \r [\r \r], \c [\c], \d [\d]}{% endraw %}
{% endhighlight %}


