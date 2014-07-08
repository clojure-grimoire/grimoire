### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (spit "blubber.txt" "test")
nil
user=> (slurp "blubber.txt")
"test"{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; To access web page. Note the use of http://
;; prefix

user=> (slurp "http://clojuredocs.org")
; This will return the html content of clojuredocs.org{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; Access absolute location on Windows

user=> (slurp "C:\\tasklists.xml")
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
;; On Linux, some JVMs have a bug where they cannot read a file in the /proc
;; filesystem as a buffered stream or reader.  A workaround to this JVM issue
;; is to open such a file as unbuffered:
(slurp (java.io.FileReader. "/proc/cpuinfo")){% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
;; You can specify what encoding to use by giving a :encoding param, and an encoding string recognized by your JVM

user=> (slurp "/path/to/file" :encoding "ISO-8859-1"){% endraw %}
{% endhighlight %}


