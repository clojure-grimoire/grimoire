### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
(with-open [rdr (clojure.java.io/reader "/tmp/foo.txt")]
    (reduce conj [] (line-seq rdr))){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (with-open [rdr (clojure.java.io/reader "http://www.google.com")]
         (printf "%s\n" (clojure.string/join "\n" (line-seq rdr))))
{% endraw %}
{% endhighlight %}


