### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(use '[clojure.java.io :only (as-url)])
(import 'java.io.File)

user=> (as-url nil)
nil

user=> (as-url (File. "/tmp"))
#<URL file:/tmp/>

user=> (as-url "http://clojuredocs.org")
#<URL http://clojuredocs.org>

user=> (as-url "http://clojuredocs.org:8080")
#<URL http://clojuredocs.org:8080>

user=> (as-url "clojuredocs.org")
#<CompilerException java.net.MalformedURLException: no protocol: clojuredocs.org>





{% endraw %}
{% endhighlight %}


