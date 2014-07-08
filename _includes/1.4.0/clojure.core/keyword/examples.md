### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Returns a keyword with the given namespace and name.  Do not use :
;; in the keyword strings, it will be added automatically.
;;
;; (keyword name): name can be string, symbol, or keyword.
;;
;; (keyword ns name): ns and name must both be string.
;;
;; A keyword string, like a symbol, begins with a non-numeric
;; character and can contain alphanumeric characters and *, +, !, -,
;; _, and ?.  (see http://clojure.org/reader for details).
;;
;; keyword does not validate input strings for ns and name, and may
;; return improper keywords with undefined behavior for non-conformant
;; ns and name.

user=> (keyword 'foo)
:foo

user=> (keyword "foo")
:foo

user=> (keyword "user" "foo")
:user/foo

;; keyword in current namespace
user=> (keyword (str *ns*) "foo")
:user/foo{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; some gotchas to be aware of:

user=> (keyword "user" 'abc)
ClassCastException clojure.lang.Symbol cannot be cast to java.lang.String  clojure.core/keyword (core.clj:558)
user=> (keyword *ns* "abc")
ClassCastException clojure.lang.Namespace cannot be cast to java.lang.String  clojure.core/keyword (core.clj:558)
user=> (keyword 'user "abc")
ClassCastException clojure.lang.Symbol cannot be cast to java.lang.String  clojure.core/keyword (core.clj:558)


;; Warning - the following generated keywords are non-conformant and may wreak
;; serious havoc in the near/far future when least expected...

user=> (keyword "abc def")
:abc def
user=> (keyword "123def")
:123def
user=> (keyword "/abc/def/ghi")
:/abc/def/ghi
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; You can define namespaced keywords using '::'
user=> (def a :foo)
#'user/a
user=> (def b ::foo)
#'user/b
user=> (ns foo)
foo=> user/a
:foo
foo=> user/b
:user/foo
foo=> ::foo
:foo/foo
foo=> (= user/a :foo)
true
foo=> (= user/b ::foo)
false
foo=> (= user/b :user/foo)
true{% endraw %}
{% endhighlight %}


