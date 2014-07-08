### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(use-fixtures :once
    (fn [your-tests]
        (spin-db)
        (your-tests)
        (kill-db)))

(deftest tests-you-want-wrapped){% endraw %}
{% endhighlight %}


