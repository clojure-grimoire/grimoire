### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (import java.util.Date)
java.util.Date

user=> (def *now* (Date.))
#'user/*now*

user=> (str *now*)
"Tue Jul 13 17:53:54 IST 2010"
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; Multiple imports at once.
(import '(java.util Date Calendar)
        '(java.net URI ServerSocket)
        java.sql.DriverManager){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; importing multiple classes in a namespace
(ns foo.bar
  (:import (java.util Date
                      Calendar)
           (java.util.logging Logger
                              Level))){% endraw %}
{% endhighlight %}


