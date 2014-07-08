### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Count lines of a file (loses head):
user=> (with-open [rdr (clojure.java.io/reader "/etc/passwd")]
         (count (line-seq rdr)))

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(import '(java.io BufferedReader StringReader))

;; line terminators are stripped
user=> (line-seq (BufferedReader. (StringReader. "1\n2\n\n3")))
("1" "2" "" "3")

;; empty string gives nil
user=> (line-seq (BufferedReader. (StringReader. "")))
nil
{% endraw %}
{% endhighlight %}


