### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}

user=> (concat [1 2] [3 4])
(1 2 3 4)

user=> (into [] (concat [1 2] [3 4]))
[1 2 3 4]

user=> (concat [:a :b] nil [1 [2 3] 4])
(:a :b 1 [2 3] 4)

=> (concat [1] [2] '(3 4) [5 6 7] #{9 10 8})
(1 2 3 4 5 6 7 8 9 10)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (concat "abc" "def")
(\a \b \c \d \e \f)
{% endraw %}
{% endhighlight %}


