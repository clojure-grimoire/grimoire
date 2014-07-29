### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (clojure.string/replace "The color is red" #"red" "blue")
"The color is blue"{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (clojure.string/replace "The color is red." #"[aeiou]"  #(str %1 %1))
"Thee cooloor iis reed."
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Note: When replace-first or replace have a regex pattern as their
;; match argument, dollar sign ($) and backslash (\) characters in
;; the replacement string are treated specially.

;; Example: first substring that the pattern matches is "fodder", with
;; (o+) matching "o" and (\S+) matching "dder".  Replacement string
;; says to replace the entire match "fodder" with $2, the string
;; matched by the second parenthesized group, "dder", followed by $1,
;; "o".
user=> (str/replace "fabulous fodder foo food" #"f(o+)(\S+)" "$2$1")
"fabulous ddero oo doo"

;; To avoid this special treatment of $ and \, you must escape them with
;; \.  Because it is in a Clojure string, to get one \ we must escape
;; *that* with its own \.
user=> (str/replace "fabulous fodder foo food" #"f(o+)(\S+)" "\\$2\\$1")
"fabulous $2$1 $2$1 $2$1"

;; To ensure the replacement is treated literally, call
;; java.util.regex.Matcher/quoteReplacement on it.  A shorter name
;; like re-qr can be handy.
user=> (import '(java.util.regex Matcher))
java.util.regex.Matcher

user=> (defn re-qr [replacement]
         (Matcher/quoteReplacement replacement))
#'user/re-qr

user=> (str/replace "fabulous fodder foo food" #"f(o+)(\S+)" (re-qr "$2$1"))
"fabulous $2$1 $2$1 $2$1"
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; replaces all a's with 1 and all b's with 2
user=>(clojure.string/replace "a b a" #"a|b" {"a" "1" "b" "2"})
"1 2 1"{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
;; Note: See clojure.core/subs for discussion of behavior of substrings
;; holding onto references of the original strings, which can
;; significantly affect your memory usage in some cases.{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
;; To title case
(clojure.string/replace "hello world" #"\b." #(.toUpperCase %1))
"Hello World"
;; Note that a vector is passed to your replacement function
;; when pattern contains capturing groups (see re-groups)
(clojure.string/replace "hello world" #"\b(.)" #(.toUpperCase (%1 1)))
"Hello World"
{% endraw %}
{% endhighlight %}


