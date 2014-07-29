### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
(defn pos-neg-or-zero
  "Determines whether or not n is positive, negative, or zero"
  [n]
  (cond
    (< n 0) "negative"
    (> n 0) "positive"
    :else "zero"))

user=> (pos-neg-or-zero 5)
"positive"
user=> (pos-neg-or-zero -1)
"negative"
user=> (pos-neg-or-zero 0)
"zero"
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (let [grade 85]
         (cond
           (>= grade 90) "A"
           (>= grade 80) "B"
           (>= grade 70) "C"
           (>= grade 60) "D"
           :else "F"))
"B"{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; See examples for "if" explaining Clojure's idea of logical true
;; and logical false.{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; Generates a random number compares it to user input
(let [rnd (rand-int 10)
      guess (Integer/parseInt (read-line))]
  (cond
    (= rnd guess) (println "You got my guess right!")
    :else (println "Sorry... guess again!"))){% endraw %}
{% endhighlight %}


