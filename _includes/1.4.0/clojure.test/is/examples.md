### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(use '[clojure.test :only [is]])

user=> (is (true? true))
true

;; false assertions print a message and evaluate to false

user=> (is (true? false))
FAIL in clojure.lang.PersistentList$EmptyList@1 (NO_SOURCE_FILE:1)
expected: (true? false)
  actual: (not (true? false))
false

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
; Testing for thrown exceptions

; Verifies that the specified exception is thrown
user=> (is (thrown? ArithmeticException (/ 1 0)))
#<ArithmeticException java.lang.ArithmeticException: Divide by zero>

; Verified that the exception is thrown, and that the error message matches the specified regular expression.
user=> (is (thrown-with-msg? ArithmeticException #"Divide by zero"
  #_=>                       (/ 1 0)))
#<ArithmeticException java.lang.ArithmeticException: Divide by zero>
user=>

{% endraw %}
{% endhighlight %}


