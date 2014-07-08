### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(:use [clojure.java.io :only [output-stream]])

(defn use-output-stream []
  (with-open [o (output-stream "test.txt")]
    (.write o 65))) ; Writes 'A'{% endraw %}
{% endhighlight %}


