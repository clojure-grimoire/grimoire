### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (spit "flubber.txt" "test")
nil
user=> (slurp "flubber.txt")
"test"{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (spit "event.log" "test 1\n" :append true)
nil

user=> (spit "event.log" "test 2\n" :append true)
nil

user=> (println (slurp "event.log"))
test 1
test 2

nil
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(defn append-to-file
  "Uses spit to append to a file specified with its name as a string, or
   anything else that writer can take as an argument.  s is the string to
   append."
  [file-name s]
  (spit file-name s :append true)){% endraw %}
{% endhighlight %}


