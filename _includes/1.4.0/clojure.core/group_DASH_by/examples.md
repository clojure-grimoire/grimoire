### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (group-by count ["a" "as" "asd" "aa" "asdf" "qwer"])
{1 ["a"], 2 ["as" "aa"], 3 ["asd"], 4 ["asdf" "qwer"]}

user=> (group-by odd? (range 10))
{false [0 2 4 6 8], true [1 3 5 7 9]}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; group by a primary key
user=> (group-by :user-id [{:user-id 1 :uri "/"}
                           {:user-id 2 :uri "/foo"}
                           {:user-id 1 :uri "/account"}])

{1 [{:user-id 1, :uri "/"} {:user-id 1, :uri "/account"}],
 2 [{:user-id 2, :uri "/foo"}]}
{% endraw %}
{% endhighlight %}


