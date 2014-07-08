### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
(defmulti service-charge (fn [acct] [(account-level acct) (:tag acct)]))
(defmethod service-charge [::acc/Basic ::acc/Checking]   [_] 25)
(defmethod service-charge [::acc/Basic ::acc/Savings]    [_] 10)
(defmethod service-charge [::acc/Premium ::acc/Account] [_] 0){% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;this example illustrates that the dispatch type
;does not have to be a symbol, but can be anything (in this case, it's a string)

(defmulti greeting
  (fn[x] (x "language")))

;params is not used, so we could have used [_]
(defmethod greeting "English" [params]
 "Hello!")

(defmethod greeting "French" [params]
 "Bonjour!")

;then can use this like this:
(def english-map {"id" "1", "language" "English"})
(def  french-map {"id" "2", "language" "French"})

=>(greeting english-map)
"Hello!"
=>(greeting french-map)
"Bounjour!"
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; Methods can be given a name.  Very useful in stack traces.
(defmethod foo "a" name-of-method [params] "was a")
{% endraw %}
{% endhighlight %}


