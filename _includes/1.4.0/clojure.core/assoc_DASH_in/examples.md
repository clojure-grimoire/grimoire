### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def users [{:name "James" :age 26}  {:name "John" :age 43}])

user=> (assoc-in users [1 :age] 44)
[{:name "James", :age 26} {:name "John", :age 44}]

user=> (assoc-in users [1 :password] "nhoJ")
[{:name "James", :age 26} {:password "nhoJ", :name "John", :age 43}]

;; Also (assoc m 2 {...}) or (conj m {...})
user=> (assoc-in users [2] {:name "Jack" :age 19})
[{:name "James", :age 26} {:name "John", :age 43} {:name "Jack", :age 19}]




;; From http://clojure-examples.appspot.com/clojure.core/assoc-in{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
(def ppl (atom {"persons" {"joe" {:age 1}}}))
(swap! ppl assoc-in ["persons" "bob"] {:age 11})

@ppl
{"persons" {"joe" {:age 1}, "bob" {:age 11}}}{% endraw %}
{% endhighlight %}


