### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (read-string "1.1")
1.1

user=> (read-string "1.1.1 (+ 1 1)")
java.lang.RuntimeException: java.lang.NumberFormatException: Invalid number: 1.1.1 (NO_SOURCE_FILE:0)

user=> (read-string "(+ 1 1)")
(+ 1 1)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (eval (read-string "(+ 1 1)"))
2

user=> (read-string (prn-str (+ 1 1)))
2
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
user=> (+ 11 (read-string "23"))
34
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
user=> (read-string "; foo\n5")
5

user=> (read-string "#^String x")
x

user=> (read-string "(1)")
(1)

user=> (read-string "(+ 1 2) (- 3 2)")
(+ 1 2)

user=> (read-string "@a")
(clojure.core/deref a)

user=> (read-string "(+ 1 2))))))")
(+ 1 2)

user=> (read-string "::whatever-namespace-you-are-in")
:user/whatever-namespace-you-are-in{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
;convert a string representing a sequence,
;to the sequence that the string represents
user=> (read-string "(\\( \\x \\y \\) \\z)")
(\( \x \y \) \z)

;then you can convert to the string that the string-sequence represents
user=> (apply str (read-string "(\\( \\x \\y \\) \\z)"))
"(xy)z"

;which is the inverse of
user=> (str (first (list (seq "(xy)z"))))
"(\\( \\x \\y \\) \\z)"{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
;; you can think of read-string as the inverse of pr-str
;; turn string into symbols
user=> (read-string "(a b foo :bar)")
(a b foo :bar)

;;turn symbols into a string
user=> (pr-str '(a b foo :bar))
"(a b foo :bar)"{% endraw %}
{% endhighlight %}


### Example 6
[permalink](#example-6)

{% highlight clojure %}
{% raw %}
;; WARNING: You SHOULD NOT use clojure.core/read-string to read data from
;; untrusted soures.  See the examples for clojure.core/read, because the same
;; issues exist for both read and read-string.{% endraw %}
{% endhighlight %}


