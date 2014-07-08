### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Instead of printing the following will place the output normally
;; sent to standard out to a string.
user=> (with-out-str (println "this should return as a string"))
"this should return as a string\n"
{% endraw %}
{% endhighlight %}


