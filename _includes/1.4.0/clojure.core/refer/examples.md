### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (refer 'clojure.string :only '[capitalize trim])
nil

user=> (capitalize (trim " hOnduRAS  "))
"Honduras"{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (refer 'clojure.string
              :rename '{capitalize cap, trim trm})
WARNING: replace already refers to: #'clojure.core/replace in namespace: user, being replaced by: #'clojure.string/replace
WARNING: reverse already refers to: #'clojure.core/reverse in namespace: user, being replaced by: #'clojure.string/reverse
nil

user=> (cap (trm " hOnduRAS  "))
"Honduras"

user=> (join \, [1 2 3])
"1,2,3"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;;; `:only' accepts only original names.
;; wrong
user=> (refer 'clojure.string
              :rename '{capitalize cap, trim trm}
              :only '[cap trm])
IllegalAccessError cap does not exist  clojure.core/refer (core.clj:3849)

;; right
user=> (refer 'clojure.string
              :rename '{capitalize cap, trim trm}
              :only '[capitalize trim])
nil

;; work well
user=> (cap (trm " hOnduRAS  "))
"Honduras"

;; and also, cannot use either of them.
user=> (join \, [1 2 3])
CompilerException java.lang.RuntimeException: Unable to resolve symbol: join in this context, compiling:(NO_SOURCE_PATH:1:1){% endraw %}
{% endhighlight %}


