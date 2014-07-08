### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; Returns a symbol with the given namespace and name.
;;
;; (symbol name): name can be a string or a symbol.
;;
;; (symbol ns name): ns and name must both be strings.
;;
;; A symbol string begins with a non-numeric character and can contain
;; alphanumeric characters and *, +, !, -, _, and ?.  (see
;; http://clojure.org/reader for details).
;;
;; symbol does not validate input strings for ns and name, and may return
;; improper symbols with undefined behavior for non-conformant ns and
;; name.

user=> (symbol 'foo)
foo

user=> (symbol "foo")
foo

user=> (symbol "clojure.core" "foo")
clojure.core/foo
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; some gotchas to be aware of:

user=> (symbol "user" 'abc)
ClassCastException clojure.lang.Symbol cannot be cast to java.lang.String  clojure.core/symbol (core.clj:523)
user=> (symbol *ns* "abc")
ClassCastException clojure.lang.Namespace cannot be cast to java.lang.String  clojure.core/symbol (core.clj:523)
user=> (symbol 'user "abc")
ClassCastException clojure.lang.Symbol cannot be cast to java.lang.String  clojure.core/symbol (core.clj:523)


;; Warning - the following generated symbols are non-conformant and may wreak
;; serious havoc in the near/far future when least expected...

user=> (symbol "abc def")
abc def
user=> (symbol "123def")
123def
user=> (symbol "/123/def/ghi")
/123/def/ghi
user=> (symbol "/abc/def/ghi")
/abc/def/ghi
{% endraw %}
{% endhighlight %}


