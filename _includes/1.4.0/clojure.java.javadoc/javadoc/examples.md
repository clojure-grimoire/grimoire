### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (use 'clojure.java.javadoc)
nil

user=> (javadoc String)
"http://java.sun.com/javase/6/docs/api/java/lang/String.html"

user=> (javadoc (java.util.Date.))
"http://java.sun.com/javase/6/docs/api/java/util/Date.html"
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user> (javadoc 1)
"http://java.sun.com/javase/7/docs/api/java/lang/Long.html"

(javadoc "abc")
"http://java.sun.com/javase/7/docs/api/java/lang/String.html"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user> (javadoc org.joda.time.DateTime)
"http://www.google.com/search?btnI=I%27m%20Feeling%20Lucky&q=allinurl:org/joda/time/DateTime.html"{% endraw %}
{% endhighlight %}


