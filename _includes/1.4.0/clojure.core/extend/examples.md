### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
; From Sean Devlin's talk on protocols at Clojure Conj
(defprotocol Dateable
  (to-ms [t]))

(extend java.lang.Number
  Dateable
  {:to-ms identity})

(extend java.util.Date
  Dateable
  {:to-ms #(.getTime %)})

(extend java.util.Calendar
  Dateable
  {:to-ms #(to-ms (.getTime %))}){% endraw %}
{% endhighlight %}


