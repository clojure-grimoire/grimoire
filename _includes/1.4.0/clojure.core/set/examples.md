### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
;; returns distinct elements
user=> (set '(1 1 2 3 2 4 5 5))
#{1 2 3 4 5}

;; returns distinct elements (different nomenclature)
user=> (set [1 1 2 3 2 4 5 5])
#{1 2 3 4 5}

user=> (set [1 2 3 4 5])
#{1 2 3 4 5}

user=> (set "abcd")
#{\a \b \c \d}

user=> (set '("a" "b" "c" "d"))
#{"a" "b" "c" "d"}

user=> (set {:one 1 :two 2 :three 3})
#{[:two 2] [:three 3] [:one 1]}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
(set [1 2 3 2 1 2 3])
-> #{1 2 3}

#{:a :b :c :d}
-> #{:d :a :b :c}

(hash-set :a :b :c :d)
-> #{:d :a :b :c}

(sorted-set :a :b :c :d)
-> #{:a :b :c :d}

;------------------------------------------------

(def s #{:a :b :c :d})
(conj s :e)
-> #{:d :a :b :e :c}

(count s)
-> 4

(seq s)
-> (:d :a :b :c)

(= (conj s :e) #{:a :b :c :d :e})
-> true

(s :b)
-> :b

(s :k)
-> nil{% endraw %}
{% endhighlight %}


