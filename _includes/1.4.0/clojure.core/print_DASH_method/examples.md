### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(deftype XYZ [])

; without custom print-method defined:
user=> (prn (XYZ.))
#<XYZ user.XYZ@2670d85b>

(defmethod print-method XYZ [v ^java.io.Writer w]
  (.write w "<<-XYZ->>"))

; with print-method
user=> (prn (XYZ.))
<<-XYZ->>
{% endraw %}
{% endhighlight %}


