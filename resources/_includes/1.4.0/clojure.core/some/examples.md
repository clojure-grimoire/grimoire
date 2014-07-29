### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
<pre>user=> (some even? '(1 2 3 4))
true
user=> (some even? '(1 3 5 7))
nil</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
<pre>
user=> (some true? [false false false])
nil
user=> (some true? [false true false])
true
user=> (some true? [true true true])
true
</pre>{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
<pre>user=&gt; (some #(= 5 %) [1 2 3 4 5])
true
user=&gt; (some #(= 5 %) [6 7 8 9 10])
nil</pre>{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
user=> (some #(when (even? %) %) '(1 2 3 4))
2{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
user=> (some {2 "two" 3 "three"} [nil 3 2])
"three"{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
;; some can be used as a substitute for (first (filter ...
;; in most cases

user=> (first (filter even? [1 2 3 4]))
2
user=> (some #(if (even? %) %) [1 2 3 4])
2
user=>{% endraw %}
{% endhighlight %}


### Example 6
[permalink](#example-6)

{% highlight clojure %}
{% raw %}
;; find a whether a word is in a list of words.
(def word "foo")
(some (partial = word) words){% endraw %}
{% endhighlight %}


### Example 7
[permalink](#example-7)

{% highlight clojure %}
{% raw %}
user=> (some #{2} (range 0 10))
2

user=> (some #{200} (range 0 10))
nil{% endraw %}
{% endhighlight %}


