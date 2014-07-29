### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (gensym "foo")
foo2020

user=> (gensym "foo")
foo2027

user=> (gensym "foo")
;; ...
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (gensym)
G__2034

user=> (let [my-unique-sym (gensym)]
         my-unique-sym)
G__2075
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; syntax-reader uses gensym for non-namespace-qualified symbols ending with '#'
;; http://clojure.org/reader

user=> `(name0#)       ; gensym, form is useful in defmacro
(name0__1206__auto__)

user=> `(user/name1#)  ; no gensym, namespace-qualified
(user/name1#)

user=> `(:key0#)       ; no gensym, keyword
(:key0#)

user=> `(::key1#)      ; no gensym, keyword
(:user/key1#)
{% endraw %}
{% endhighlight %}


