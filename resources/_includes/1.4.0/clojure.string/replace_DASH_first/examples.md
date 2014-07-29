### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (use '[clojure.string :only (replace-first)])

;; Only replace the first match.
user=> (replace-first "A good day to you, sir.  Good day." #"day" "night")
"A good night to you, sir.  Good day."

;; If there are no matches, return the original string.
user=> (replace-first "A good day to you, sir." #"madam" "master")
"A good day to you, sir."

;; (?i) at the beginning of a pattern makes the entire thing match
;; case-insensitively, at least for US ASCII characters.  (?u) does
;; the corresponding thing for Unicode.
user=> (replace-first "Day need not be SHOUTED." #"(?i)day" "night")
"night need not be SHOUTED."

;; See here for many details on regex patterns:
;; http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
;; Also the book "Mastering Regular Expressions" by Jeffrey Friedl.
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (use '[clojure.string :only (replace-first)])

;; Pattern matches from beginning of string (signified by ^) up to the
;; last occurrence of /, because by default * is greedy, i.e. it
;; matches as much as possible.
user=> (replace-first "/path/to/file/name" #"^.*/" "")
"name"

;; Use *? to match as little as possible.
user=> (replace-first "/path/to/file/name" #"^.*?/" "")
"path/to/file/name"
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Note: When replace-first or replace have a regex pattern as their
;; match argument, dollar sign ($) and backslash (\) characters in
;; the replacement string are treated specially.

;; Example: pattern matches string "fodder", with (o+) matching "o"
;; and (\S+) matching "dder".  Replacement string says to replace the
;; entire match "fodder" with $2, the string matched by the second
;; parenthesized group, "dder", followed by $1, "o".
user=> (str/replace-first "fabulous fodder foo food" #"f(o+)(\S+)" "$2$1")
"fabulous ddero foo food"

;; To avoid this special treatment of $ and \, you must escape them with
;; \.  Because it is in a Clojure string, to get one \ we must escape
;; *that* with its own \.
user=> (str/replace-first "fabulous fodder foo food" #"f(o+)(\S+)" "\\$2\\$1")
"fabulous $2$1 foo food"

;; To ensure the replacement is treated literally, call
;; java.util.regex.Matcher/quoteReplacement on it.  A shorter name
;; like re-qr can be handy.
user=> (import '(java.util.regex Matcher))
java.util.regex.Matcher

user=> (defn re-qr [replacement]
         (Matcher/quoteReplacement replacement))
#'user/re-qr

user=> (str/replace-first "fabulous fodder foo food" #"f(o+)(\S+)" (re-qr "$2$1"))
"fabulous $2$1 foo food"
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


