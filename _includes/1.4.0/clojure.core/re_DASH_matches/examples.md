### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; The distinction is that re-find tries to find _any part_ of the string
;; that matches the pattern, but re-matches only matches if the _entire_
;; string matches the pattern.
user=> (re-matches #"hello" "hello, world")
nil

user=> (re-matches #"hello.*" "hello, world")
"hello, world"

user=> (re-matches #"hello, (.*)" "hello, world")
["hello, world" "world"]
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Note: See clojure.core/subs for discussion of behavior of substrings
;; holding onto references of the original strings, which can
;; significantly affect your memory usage in some cases.{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
; Regex match flags can be embedded in the regex string.  So, we can convert the normal case-sensitive matching into case-insensitive matching.

user=> (re-matches #"hello" "HELLO")       ; case-sensitive
nil

user=> (re-matches #"(?i)hello" "hello")   ; case-insensitive
"hello"
user=> (re-matches #"(?i)hello" "HELLO")   ; case-insensitive
"HELLO"
user=> (re-matches #"(?i)HELLO" "heLLo")   ; case-insensitive
"heLLo"
{% endraw %}
{% endhighlight %}


