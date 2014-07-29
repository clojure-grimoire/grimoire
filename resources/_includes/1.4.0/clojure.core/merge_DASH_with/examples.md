### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (merge-with concat
		  {"Lisp" ["Common Lisp" "Clojure"]
		   "ML" ["Caml" "Objective Caml"]}
		  {"Lisp" ["Scheme"]
		   "ML" ["Standard ML"]})
{"Lisp" ("Common Lisp" "Clojure" "Scheme"), "ML" ("Caml" "Objective Caml" "Standard ML")}

user=> (clojure.pprint/pp)
{"Lisp" ("Common Lisp" "Clojure" "Scheme"), "ML" ("Caml" "Objective Caml" "Standard ML")}
nil
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; merge two maps using the addition function

user=> (merge-with +
                   {:a 1  :b 2}
                   {:a 9  :b 98 :c 0})

{:c 0, :a 10, :b 100}{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; 'merge-with' works with an arbitrary number of maps:

user=> (merge-with +
                   {:a 1  :b 2}
                   {:a 9  :b 98  :c 0}
                   {:a 10 :b 100 :c 10}
                   {:a 5}
                   {:c 5  :d 42})

{:d 42, :c 15, :a 25, :b 200}{% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; Use union to merge sets of elements
user=> (use 'clojure.set)
user=> (merge-with union
                   {:a #{1 2 3},   :b #{4 5 6}}
                   {:a #{2 3 7 8}, :c #{1 2 3}})

{:c #{1 2 3}, :a #{1 2 3 7 8}, :b #{4 5 6}}{% endraw %}
{% endhighlight %}


