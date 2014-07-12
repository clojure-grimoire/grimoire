### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user> (def players (atom ()))
#'user/players

user> (swap! players conj :player1)
(:player1)

user> (swap! players conj :player2)
(:player2 :player1)

user> (deref players)
(:player2 :player1){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user> (def counter (atom 0))
#'user/counter

user> (swap! counter inc)
1

user> (swap! counter inc)
2{% endraw %}
{% endhighlight %}


