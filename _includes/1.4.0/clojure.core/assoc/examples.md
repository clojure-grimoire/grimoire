### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (assoc {} :key1 "value" :key2 "another value")
{:key2 "another value", :key1 "value"}

user=> (assoc {:key1 "old value1" :key2 "value2"} :key1 "value1" :key3 "value3")
{:key3 "value3", :key2 "value2", :key1 "value1"}

user=> (assoc nil :key1 4)
{:key1 4}

user=> (assoc [1 2 3] 0 10)
[10 2 3]

user=> (assoc [1 2 3] 3 10)
[1 2 3 10]

user=> (assoc [1 2 3] 4 10)
java.lang.IndexOutOfBoundsException (NO_SOURCE_FILE:0)




;; From http://clojure-examples.appspot.com/clojure.core/assoc{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(def test-map {:account-no 12345678 :lname "Jones" :fnam "Fred"})
(assoc test-map :fnam "Sue")
{:account-no 12345678, :lname "Jones", :fnam "Sue"}{% endraw %}
{% endhighlight %}


