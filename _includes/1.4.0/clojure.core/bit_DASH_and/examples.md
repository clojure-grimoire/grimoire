### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (bit-and 2r1100 2r1001)
8
;; 8 = 2r1000

;; the same in decimal
user=> (bit-and 12 9)
8{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user> (bit-and 0x08 0xFF)
8{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (Integer/toBinaryString 235)
"11101011"
user=> (Integer/toBinaryString 199)
"11000111"
user=> (bit-and 235 199)
195
user=> (Integer/toBinaryString 195)
"11000011"

;;11101011
;;&
;;11000111
;;=
;;11000011{% endraw %}
{% endhighlight %}


