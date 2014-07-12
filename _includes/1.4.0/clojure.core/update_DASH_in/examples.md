### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (def users [{:name "James" :age 26}  {:name "John" :age 43}])
#'user/users

user=> (update-in users [1 :age] inc)

[{:name "James", :age 26} {:name "John", :age 44}]
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (def p {:name "James" :age 26})
#'user/p

user=> (update-in p [:age] inc)
{:name "James", :age 27}

;; remember, the value of p hasn't changed!
user=> (update-in p [:age] + 10)
{:name "James", :age 36}

{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
(defn char-cnt [s]
  "Counts occurence of each character in s"
  (reduce
    (fn [m k]
      (update-in m [k] (fnil inc 0)))
  {}
  (seq s)))
;Note use of fnil above - returns 0 if nil is passed to inc (avoids null pointer exception)
{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
; f has args
user=> (update-in {:a 3} [:a] / 4 5)
{:a 3/20}{% endraw %}
{% endhighlight %}


