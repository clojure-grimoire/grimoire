### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; If you try this, you're doing it wrong
user=> (create-ns my-new-namespace)
java.lang.Exception: Unable to resolve symbol: my-new-namespace in this context (NO_SOURCE_FILE:2)


;; Same deal, if you try this, you're doing it wrong
user=> (create-ns "my-new-namespace")
java.lang.ClassCastException: java.lang.String cannot be cast to clojure.lang.Symbol (NO_SOURCE_FILE:0)


;; This is how you do it
user=> (create-ns 'my-new-namespace)
#&lt;Namespace my-new-namespace&gt;
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; Let's create a namespace and check for our result
;; the new namespace will be "my-new-namespace"

;; obviously, it does not exist yet, so looking for it, finds nothing
user=> (find-ns 'my-new-namespace)
nil

;; let's create it
user=> (create-ns 'my-new-namespace)
#&lt;Namespace my-new-namespace&gt;

;; now searching for it again will have a result
user=> (find-ns 'my-new-namespace)
#&lt;Namespace my-new-namespace&gt;
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; You can create a namespace, not switch to it and still work in, by storing it

;; create the namespace
user=> (def for-later-use (create-ns 'my-namespace))
#'user/for-later-use

;; assign a value for a variable
user=> (intern for-later-use 'my-var "some value")
#'my-namespace/my-var
;; notice how the "for-later-use" symbol has been evaluated to the namespace it represents

;; check the new variable
user=> my-namespace/my-var
"some value"

;; you can also work on a namespace by using the its name
;; (but quoting it) instead of the return of "create-ns"
user=> (intern 'my-namespace 'my-var "some other value")
#'my-namespace/my-var

;; check the new assignment and see what's changed
user=> my-namespace/my-var
"some other value"
{% endraw %}
{% endhighlight %}


