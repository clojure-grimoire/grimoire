### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(ns-map 'clojure.core)
{sorted-map #'clojure.core/sorted-map, read-line #'clojure.core/read-line, re-pattern #'clojure.core/re-pattern, keyword? #'clojure.core/keyword?, ClassVisitor clojure.asm.ClassVisitor, asm-type #'clojure.core/asm-type, val #'clojure.core/val, ...chop...}{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; See also http://clojure.org/namespaces for information on namespaces in Clojure and how to inspect and manipulate them{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; ns-map = ns-refers + ns-interns + ns-imports
user=> (count (ns-imports *ns*))
;;=> 96

user=> (count (ns-interns *ns*))
;;=> 2

user=> (count (ns-refers *ns*))
;;=> 590

user=> (+ *1 *2 *3)
;;=> 688

user=> (count (ns-map *ns*))
;;=> 688{% endraw %}
{% endhighlight %}


