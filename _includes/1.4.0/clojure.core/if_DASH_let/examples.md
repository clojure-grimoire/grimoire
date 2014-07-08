### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
user=> (defn sum-even-numbers [nums]
         (if-let [nums (seq (filter even? nums))]
           (reduce + nums)
           "No even numbers found."))
#'user/sum-even-numbers

user=> (sum-even-numbers [1 3 5 7 9])
"No even numbers found."

user=> (sum-even-numbers [1 3 5 7 9 10 12])
22
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
user=> (if-let [x false y true]
         "then"
         "else")
java.lang.IllegalArgumentException: if-let requires exactly 2 forms in binding vector (NO_SOURCE_FILE:1)

user=> (defn if-let-demo [arg]
         (if-let [x arg]
           "then"
           "else"))

user=> (if-let-demo 1) ; anything except nil/false
"then"
user=> (if-let-demo nil)
"else"
user=> (if-let-demo false)
"else"
{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
; This macro is nice when you need to calculate something big. And you need
; to use the result but only when it's true:

(if-let [life (meaning-of-life 12)]
   life
   (if-let [origin (origin-of-life 1)]
      origin
      (if-let [shot (who-shot-jr 5)]
         block-sol
	 42)))

; As you can see in the above example it will return the answer
; to the question only if the answer is not nil. If the answer
; is nil it will move to the next question. Until finally it
; gives up and returns 42.{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure linenos %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure linenos %}
{% raw %}
;;; with distructuring binding

;; successful case
(if-let [[w n] (re-find #"a(\d+)x" "aaa123xxx")]
  [w n]
  :not-found)  ;=> ["a123x" "123"]

;; unsuccessful case
(if-let [[w n] (re-find #"a(\d+)x" "bbb123yyy")]
  [w n]
  :not-found) ;=> :not-found

;; same as above
(if-let [[w n] nil]
  [w n]
  :not-found) ;=> :not-found

;; on Map
(if-let [{:keys [a b]} nil]
  [a b]
  :not-found) ;=> :not-found
{% endraw %}
{% endhighlight %}


