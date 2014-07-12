### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
;; Each node is a number or a seq,
;; so branch?==seq? and children==identity
;;
;;     .
;;    / \
;;   .   .
;;  /|\  |
;; 1 2 . 4
;;     |
;;     3
;;

user=> (tree-seq seq? identity '((1 2 (3)) (4)))

(((1 2 (3)) (4)) (1 2 (3)) 1 2 (3) 3 (4) 4)
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
user=> (tree-seq map? #(interleave (keys %) (vals %)) {:a 1 :b {:c 3 :d 4 :e {:f 6 :g 7}}})

({:a 1, :b {:c 3, :d 4, :e {:f 6, :g 7}}} :a 1 :b {:c 3, :d 4, :e {:f 6, :g 7}} :c 3 :d 4 :e {:f 6, :g 7} :f 6 :g 7){% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
;; Each node is a (node-root child1 child2 ...),
;; so branch?==next and children==rest
;;
;;     A
;;    / \
;;   B   C
;;  / \  |
;; D   E F
;;
user=> (map first (tree-seq next rest '(:A (:B (:D) (:E)) (:C (:F)))))

(:A :B :D :E :C :F){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
;; FYI, previous example doesn't always work:
;;
user> (map first (tree-seq next rest '((1 2 (3)) (4))))
((1 2 (3)) 4)
{% endraw %}
{% endhighlight %}


### Example 4
[permalink](#example-4)

{% highlight clojure %}
{% raw %}
user=> (tree-seq seq? seq [[1 2 [3]] [4]])
([[1 2 [3]] [4]])

user=> (tree-seq vector? seq [[1 2 [3]] [4]])
([[1 2 [3]] [4]] [1 2 [3]] 1 2 [3] 3 [4] 4)

user=> (tree-seq seq? seq '((1 2 (3)) (4)))
(((1 2 (3)) (4)) (1 2 (3)) 1 2 (3) 3 (4) 4)
{% endraw %}
{% endhighlight %}


