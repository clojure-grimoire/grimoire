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

;;default handling
(defmethod greeting :default [params]
 (throw (IllegalArgumentException.
          (str "I don't know the " (params "language") " language"))))

;then can use this like this:
(def english-map {"id" "1", "language" "English"})
(def  french-map {"id" "2", "language" "French"})
(def spanish-map {"id" "3", "language" "Spanish"})

=>(greeting english-map)
"Hello!"
=>(greeting french-map)
"Bounjour!"
=>(greeting spanish-map)
 java.lang.IllegalArgumentException: I don't know the Spanish language{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;; Implementing factorial using multimethods Note that factorial-like function
;; is best implemented using `recur` for enable tail-call optimization to avoid
;; stack overflow error. This is a only a demonstration of clojure's multimethod

;; identity form returns the same value passed
(defmulti factorial identity)

(defmethod factorial 0 [_]  1)
(defmethod factorial :default [num]
    (* num (factorial (dec num))))

(factorial 0) ; => 1
(factorial 1) ; => 1
(factorial 3) ; => 6
(factorial 7) ; => 5040{% endraw %}
{% endhighlight %}


