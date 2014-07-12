### Example 0
[permalink](#example-0)

{% highlight clojure %}
{% raw %}
user=> (conj [1 2 3] 4)
[1 2 3 4]

user=> (conj '(1 2 3) 4)
(4 1 2 3)

user=> (conj ["a" "b" "c"] "d")
["a" "b" "c" "d"]

user=> (conj [1 2] 3 4)
[1 2 3 4]

user=> (conj [[1 2] [3 4]] [5 6])
[[1 2] [3 4] [5 6]]

;; Maps only take vectors of length exactly 2
user=> (conj {1 2, 3 4} [5 6])
{5 6, 1 2, 3 4}

user=> (conj {:firstname "John" :lastname "Doe"} {:age 25 :nationality "Chinese"})
{:nationality "Chinese", :age 25, :firstname "John", :lastname "Doe"}

;; conj on a set
user=> (conj #{1 3 4} 2)
#{1 2 3 4}

{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure %}
{% raw %}
;conjoin shows similar behaviour to cons
(conj ["a" "b" "c"] ["a" "b" "c"] )
user=> ["a" "b" "c" ["a" "b" "c"]]{% endraw %}
{% endhighlight %}


### Example 2
[permalink](#example-2)

{% highlight clojure %}
{% raw %}
; conj nil with x or xs
user=> (conj nil 3)
(3)
user=> (conj nil 3 4)
(4 3){% endraw %}
{% endhighlight %}


### Example 3
[permalink](#example-3)

{% highlight clojure %}
{% raw %}
; maps and sets are treated differently
user=> (conj {1 2} {3 4})
{3 4, 1 2}   ; the contents of {3 4} are added to {1 2}

user=> (conj #{1 2} #{3})
#{1 2 #{3}}  ; the whole set #{3} is added to #{1 2}

user=> (clojure.set/union #{1 2} #{3})
#{1 2 3}  ; must use (clojure.set/union) to merge sets, not conj
{% endraw %}
{% endhighlight %}


