### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (rename-keys {:a 1, :b 2} {:a :new-a, :b :new-b})
{:new-a 1, :new-b 2}


;; The behavior when the second map contains a key not in the first is interesting.
;; I suspect you shouldn't depend on it. (Clojure 1.1 - no longer happens in 1.2.1)

user=> (rename-keys {:a 1} {:b :new-b})
{ :a 1, :new-b nil}
{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;; You need to be careful about key collisions.  You probably shouldn't
;; depend on the exact behavior.
user=> (rename-keys {:a 1 :b 2} {:a :b})
{:b 1}

user=> (rename-keys  {:a 1 :b 2}  {:a :b :b :a})
{:a 1}

;; You can work around key collisions by using an array-map to control
;; the order of the renamings.
user=> (rename-keys  {:a 1 :b 2 :c 3}  (array-map :a :tmp :b :a :tmp :b))
{:b 1, :a 2, :c 3}
{% endraw %}
{% endhighlight %}


