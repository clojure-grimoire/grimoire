### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
=> (try
     (/ 1 0)
     (catch Exception e (str "caught exception: " (.getMessage e))))

"caught exception: Divide by zero"{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; for Clojurescript use js/Object as type
(try
   (/ 1 0)
   (catch js/Object e
       (.log js/console e))){% endraw %}
{% endhighlight %}


