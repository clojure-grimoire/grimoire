### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Very useful from a REPL
;; Paths are specified as strings using canonical file path notation
;; (rather than clojure-style namespaces dependent on the JVM classpath).
;; The working directory is set to wherever you invoked the JVM from,
;; likely the project root.

(load-file "src/mylib/core.clj")

;; now you can go and evaluate vars defined in that file.{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; file located at src/address_book/core.clj
;; current dir is src/..

(load-file "src/address_book/core.clj"){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; create a clojure file on the fly using spit
;; then load it into the REPL and use its function

user=> (spit "mycode.clj" "(defn doub [x] (* x 2))")
nil
user=> (load-file "mycode.clj")
#'user/doub
user=> (doub 23)
46
user=>{% endraw %}
{% endhighlight %}


