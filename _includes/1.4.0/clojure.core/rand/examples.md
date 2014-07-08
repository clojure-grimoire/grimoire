### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Test `rand` never returns `n`:
user=> (some (partial <= 10) (take 100000 (repeatedly (fn [] (int (rand 10))))))
nil
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (rand)
0.17469201779243182

user=> (rand 100)
49.542391492950834{% endraw %}
{% endhighlight %}


