### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; See http://download.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html
;; for formatting options.
user=> (format "Hello there, %s" "bob")
"Hello there, bob"
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (format "%5d" 3)
"    3"

user=> (format "Pad with leading zeros %07d" 5432)
"Pad with leading zeros 0005432"

user=> (format "Left justified :%-7d:" 5432)
"Left justified :5432   :"

user=> (format "Locale-specific group separators %,12d" 1234567)
"Locale-specific group separators    1,234,567"

user=> (format "decimal %d  octal %o  hex %x  upper-case hex %X" 63 63 63 63)
"decimal 63  octal 77  hex 3f  upper-case hex 3F"

user=> (format "%2$d %1$s" "Positional arguments" 23)
"23 Positional arguments"

;;    ====== Clojure format/printf and large integers =====

;; This big number doesn't fit in a Long.  It is a
;; clojure.lang.BigInt, which format cannot handle directly.
user=> (format "%5d" 12345678901234567890)
IllegalFormatConversionException d != clojure.lang.BigInt  java.util.Formatter$FormatSpecifier.failConversion (Formatter.java:3999)

;; You can convert it to a java.math.BigInteger, which format does handle.
user=> (format "%5d" (biginteger 12345678901234567890))
"12345678901234567890"

;; If you do this very often, you might want to use something like
;; format-plus to avoid sprinkling your code with calls to biginteger.
(defn coerce-unformattable-types [args]
  (map (fn [x]
         (cond (instance? clojure.lang.BigInt x) (biginteger x)
               (instance? clojure.lang.Ratio x) (double x)
               :else x))
       args))

(defn format-plus [fmt & args]
  (apply format fmt (coerce-unformattable-types args)))

;; Now this works:
user=> (format-plus "%5d" 12345678901234567890)
"12345678901234567890"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;;  ==== Clojure format/printf and floating-point formats ====
user=> (format "%.3f" 2.0)
"2.000"

;; format doesn't handle integers or ratios with %e, %f, %g, or %a
user=> (format "%.3f" 2)
IllegalFormatConversionException f != java.lang.Long  java.util.Formatter$FormatSpecifier.failConversion (Formatter.java:3999)

;; In general, if you want to use floating-point formats %e, %f, %g,
;; or %a with format or printf, and you don't know whether the values
;; you want to format are floats or doubles, you should convert them:
user=> (format "%.3f" (double 2))
"2.000"

user=> (format "%.3f" (double (/ 5 2)))
"2.500"

;; One could make a function that parses the format string to look for
;; %f and other floating-point formats and automatically coerces the
;; corresponding arguments to doubles, but such a function probably
;; wouldn't fit into a short example.  You could also consider using
;; cl-format which does handle these kinds of things for you.  The main
;; disadvantage to doing so is that you have to learn a different syntax
;; for format specifiers.{% endraw %}
{% endhighlight %}


