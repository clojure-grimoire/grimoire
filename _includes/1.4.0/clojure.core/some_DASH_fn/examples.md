### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
<pre>
user=> ((some-fn even?) 1)
false
user=> ((some-fn even?) 2)
true
user=> ((some-fn even?) 1 2)
true
</pre>{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
;; `some-fn` is useful for when you'd use `some` (to find out if any
;; values in a given coll satisfy some predicate), but have more than
;; one predicate. For example, to check if any values in a coll are
;; either even or less than 10:

(or (some even? [1 2 3])
    (some #(< % 10) [1 2 3]))

;; but `some-fn` can save you some duplication here:

((some-fn even? #(< % 10)) 1 2 3)

;; Minor note: the former returns nil if it doesn't find
;; what it's looking for. The latter returns false.{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure linenos %}
{% raw %}
;;; http://en.wikipedia.org/wiki/Fizz_buzz
(def fizzbuzz
  (some-fn #(and (= (mod % 3) 0) (= (mod % 5) 0) "FizzBuzz")
           #(and (= (mod % 3) 0) "Fizz")
           #(and (= (mod % 5) 0) "Buzz")))

(doseq [n (take 17 (rest (range)))]
  (println (or (fizzbuzz n) n)))

1
2
Fizz
4
Buzz
Fizz
7
8
Fizz
Buzz
11
Fizz
13
14
FizzBuzz
16
17{% endraw %}
{% endhighlight %}


