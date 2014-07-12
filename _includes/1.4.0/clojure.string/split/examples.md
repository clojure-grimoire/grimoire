### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (clojure.string/split "q1w2e3r4t5y6u7i8o9p0" #"\d+")
["q" "w" "e" "r" "t" "y" "u" "i" "o" "p"]
user=> (clojure.string/split "q1w2e3r4t5y6u7i8o9p0" #"\d+" 5)
["q" "w" "e" "r" "t5y6u7i8o9p0"]
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Splits a string on space character and joins
;; the resulting collection with a line feed character

(use '[clojure.string :only (join split)])

user=> (println
         (join "\n"
           (split "The Quick Brown Fox" #"\s")))
The
Quick
Brown
Fox
nil{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(use '[clojure.string :only (split triml)])

;; Splitting on whitespace is a common desire.
user=> (split "Some words to split" #"\s+")
["Some" "words" "to" "split"]

;; By using the pattern #"\s+", we split on all occurrences of one or
;; more consecutive whitespace characters.
user=> (split "Some    words   with\tother whitespace      \n" #"\s+")
["Some" "words" "with" "other" "whitespace"]

;; If you are used to Perl's special behavior of split(' ', $str),
;; where it ignores leading whitespace in the string to be split, this
;; does not quite do it.
user=> (split "   Leading whitespace causes empty first string" #"\s+")
["" "Leading" "whitespace" "causes" "empty" "first" "string"]

;; This will do it.
user=> (defn perl-split-on-space [s]
         (split (triml s) #"\s+"))
#'user/perl-split-on-space
user=> (perl-split-on-space "   This is often what you want   ")
["This" "is" "often" "what" "you" "want"]

;; There might be cases where you want this instead.
user=> (split "Some    words   with\tother whitespace      \n" #"\s")
["Some" "" "" "" "words" "" "" "with" "other" "whitespace"]
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
(use '[clojure.string :only (split)])

;; Split on every occurrence of : character
user=> (split "root:*:0:0:admin:/var/root:/bin/sh" #":")
["root" "*" "0" "0" "admin" "/var/root" "/bin/sh"]

;; Empty strings are returned when two colons appear consecutively in
;; the string to be split.
user=> (split "root::0:0::/var/root:/bin/sh" #":")
["root" "" "0" "0" "" "/var/root" "/bin/sh"]

;; Without specifying a limit, any empty strings at the end are
;; omitted.
user=> (split "root::0:0:admin:/var/root:" #":")
["root" "" "0" "0" "admin" "/var/root"]
user=> (split "root::0:0:admin::" #":")
["root" "" "0" "0" "admin"]

;; If you want all of the fields, even trailing empty ones, use a
;; negative limit.
user=> (split "root::0:0:admin:/var/root:" #":" -1)
["root" "" "0" "0" "admin" "/var/root" ""]
user=> (split "root::0:0:admin::" #":" -1)
["root" "" "0" "0" "admin" "" ""]

;; Use a positive limit of n to limit the maximum number of strings in
;; the return value to n.  If it returns exactly n strings, the last
;; contains everything left over after splitting off the n-1 earlier
;; strings.
user=> (split "root::0:0:admin:/var/root:" #":" 2)
["root" ":0:0:admin:/var/root:"]
user=> (split "root::0:0:admin:/var/root:" #":" 3)
["root" "" "0:0:admin:/var/root:"]
user=> (split "root::0:0:admin:/var/root:" #":" 4)
["root" "" "0" "0:admin:/var/root:"]
user=> (split "root::0:0:admin:/var/root:" #":" 15)
["root" "" "0" "0" "admin" "/var/root" ""]
{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
(:require [clojure.string :as cstr])

(def legal-ref "1321-61")

(cstr/split legal-ref #"-")
["1321" "61"]{% endraw %}
{% endhighlight %}


### Example 5
[permalink](#example-5)

{% highlight clojure %}
{% raw %}
;; Note: See clojure.core/subs for discussion of behavior of substrings
;; holding onto references of the original strings, which can
;; significantly affect your memory usage in some cases.{% endraw %}
{% endhighlight %}


