### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def *matcher* (re-matcher #"\d+" "abc12345def"))
#'user/*matcher*

user=> (re-find *matcher*)
"12345"

;; If you only want the first match, it is shorter to call re-find with the
;; pattern and the string to search, rather than explicitly creating a matcher
;; as above.
user=> (re-find #"\d+" "abc12345def")
"12345"

;; If you want all matches as a sequence, use re-seq.  Creating a matcher
;; explicitly with re-matcher and passing it to re-find is only the best way
;; if you want to write a loop that iterates through all matches, and do not
;; want to use re-seq for some reason.
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; re-find can be used to iterate through re matches in the string

user=> (def phone-number "672-345-456-3212")
#'user/phone-number

user=> (def matcher (re-matcher #"\d+" phone-number))
#'user/matcher

user=> (re-find matcher)
"672"

user=> (re-find matcher)
"345"

user=> (re-find matcher)
"456"

user=> (re-find matcher)
"3212"

;; when there's no more valid matches, nil is returned
user=> (re-find matcher)
nil{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; When there are parenthesized groups in the pattern and re-find
;; finds a match, it returns a vector.  The first item is the part of
;; the string that matches the entire pattern, and each successive
;; item are the parts of the string that matched the 1st, 2nd,
;; etc. parenthesized groups.  Groups are numbered by the order in
;; which their left parenthesis occurs in the string, from left to
;; right.
user=> (def line " RX packets:1871074138 errors:5 dropped:48 overruns:9")
#'user/line

user=> (re-find #"(\S+):(\d+)" line)
["packets:1871074138" "packets" "1871074138"]

;; groups can nest
user=> (re-find #"(\S+:(\d+)) \S+:\d+" line)
["packets:1871074138 errors:5" "packets:1871074138" "1871074138"]

;; If there is no match, re-find always returns nil, whether there are
;; parenthesized groups or not.
user=> (re-find #"(\S+):(\d+)" ":2 numbers but not 1 word-and-colon: before")
nil

;; A parenthesized group can have nil as its result if it is part of
;; an 'or' (separated by | in the regex), and another alternative is
;; the one that matches.
user=> (re-find #"(\D+)|(\d+)" "word then number 57")
["word then number " "word then number " nil]

user=> (re-find #"(\D+)|(\d+)" "57 number then word")
["57" nil "57"]

;; It is also possible for a group to match the empty string.
user=> (re-find #"(\d*)(\S)\S+" "lots o' digits 123456789")
["lots" "" "l"]

;; If you want to use parentheses to group a part of the regex, but
;; have no interest in capturing the string it matches, you can follow
;; the left paren with ?: to prevent capturing.
user=> (re-find #"(?:\S+):(\d+)" line)
["packets:1871074138" "1871074138"]

;; re-matches also behaves this way, and re-seq returns a sequence of
;; matches, where each one can be a vector like re-find returns.
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;;It's possible to get variables out of your string with regexp

user=> (re-find #"(\d\d\d) (USD)" "450 USD")
["450 USD" "450" "USD"]
user=> (nth *1 1)
"450"

;;thanks kotarak @ stackoverflow.com for this one{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
;; If your input has line delimiters you can switch on multiline with (?m)

user=> (def testcase "Line 1\nLine 2\nTarget Line\nLine 4\nNot a target line")
user=>(re-find #"(?im)^target.*$" testcase)
"Target Line"{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
;; Note: See clojure.core/subs for discussion of behavior of substrings
;; holding onto references of the original strings, which can
;; significantly affect your memory usage in some cases.{% endraw %}
{% endhighlight %}


