### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Let's create new namespace, create new variable in it, then access it from another namespace

;; create the namespace and switch to it
user=> (in-ns 'first-namespace)
#&lt;Namespace first-namespace&gt;

;; create a variable and check it
first-namespace=> (def my-var "some value")
#'first-namespace/my-var
first-namespace=> my-var
"some value"

;; create another namespace and switch to this one
first-namespace=> (in-ns 'second-namespace)
#&lt;Namespace second-namespace&gt;

;; use variable from the other namespace here
second-namespace=> first-namespace/my-var
"some value"{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; The "in-ns" function works almost the same as "ns", but does not load clojure.core

user=> (in-ns 'my-namespace)
#&lt;Namespace my-namespace&gt;

;; the function clojure.core/inc won't just work
my-namespace=> (inc 1)
java.lang.Exception: Unable to resolve symbol: inc in this context (NO_SOURCE_FILE:15)

my-namespace=> (clojure.core/inc 1)
2
{% endraw %}
{% endhighlight %}


