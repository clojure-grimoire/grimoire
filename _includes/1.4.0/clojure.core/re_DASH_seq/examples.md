### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (re-seq #"\d" "clojure 1.1.0")
("1" "1" "0")
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Get a sequence of words out of a string.
user=> (re-seq #"\w+" "mary had a little lamb")
("mary" "had" "a" "little" "lamb")
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Parenthesized groups in the regex cause each returned match to be a
;; vector of matched strings.  See re-find for more examples.
user=> (def line " RX pkts:18 err:5 drop:48")
#'user/line

user=> (re-seq #"(\S+):(\d+)" line)
(["pkts:18" "pkts" "18"] ["err:5" "err" "5"] ["drop:48" "drop" "48"])
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; Note: See clojure.core/subs for discussion of behavior of substrings
;; holding onto references of the original strings, which can
;; significantly affect your memory usage in some cases.{% endraw %}
{% endhighlight %}


