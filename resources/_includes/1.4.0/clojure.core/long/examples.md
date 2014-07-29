### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (let [num (* 1234567890 21)] [num (int num) (long num)])
[25925925690 156121914 25925925690]{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (= 21 (long 21))
true

;; but
user=> (.equals 21 (long 21))
false

;; and thus
user=> (get {21 :twenty-one} (long 21))
nil
{% endraw %}
{% endhighlight %}


